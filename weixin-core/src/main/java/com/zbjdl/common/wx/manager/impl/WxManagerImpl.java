package com.zbjdl.common.wx.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.zbjdl.common.httpclient.HttpClientUtil;
import com.zbjdl.common.httpclient.MethodType;
import com.zbjdl.common.json.JsonMapper;
import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigRepository;
import com.zbjdl.common.utils.config.ConfigurationUtils;
import com.zbjdl.common.utils.config.dao.ConfigDao;
import com.zbjdl.common.utils.config.entity.Config;
import com.zbjdl.common.utils.config.enumtype.ValueDataTypeEnum;
import com.zbjdl.common.utils.config.enumtype.ValueTypeEnum;
import com.zbjdl.common.utils.config.impl.DefaultConfigRepository;
import com.zbjdl.common.wechat.dto.WeixinAppConfig;
import com.zbjdl.common.wx.manager.WxManager;
import com.zbjdl.common.wx.util.ConfigEnum;
import com.zbjdl.common.wx.util.ConfigUtils;
import com.zbjdl.common.wx.util.dto.OAuthAccessTokenDto;
import com.zbjdl.common.wx.util.dto.WxTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("wxManager")
public class WxManagerImpl implements WxManager {
	
	private Logger logger = LoggerFactory.getLogger(WxManagerImpl.class);
	private final String  WEIXIN_ACCESS_TOKEN_KEY_PREFIX = "WEIXIN_ACCESS_TOKEN_";
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	/**
	 * 将Map数据转换为JSON字符串，供发送消息使用
	 * 
	 * @since 2015年4月23日 下午1:56:45
	 * @param wxTemplate     模板消息体
	 * @return json          将对象转化为json字符串
	 */
	@Override
	public String preSendMessage(WxTemplate wxTemplate) {
		JsonMapper mapper = new JsonMapper();
		return mapper.toJson(wxTemplate);
	}
	
	

	
	/**
	 * 将消息发送到微信
	 */
	public <T>T sendToWx(String url , Class<T> clazz , Object... params) throws HttpException, IOException{
		url = String.format(url, params);
		logger.info("调用微信接口:{}" , url);
		HttpClientUtil util = new HttpClientUtil();
		String response = util.doRequest(MethodType.GET, url, "UTF-8");
		logger.info("微信接口返回信息:{}" , response);
		JsonMapper mapper = new JsonMapper();
		return mapper.fromJson(response, clazz);
	}
	
	/**
	 * 获取新的Access_token
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String getAccessToken(WeixinAppConfig weixinAppConfig,boolean isAnyCase) throws HttpException, IOException{
		
		//从缓存中获取AccessToken 。如果不存在，则直接去获取，并放入缓存
		//不同的微信账号的ACCESS_TOKEN 的key 为：WEIXIN_ACCESS_TOKEN_+AppId
		String accessTokenKey =WEIXIN_ACCESS_TOKEN_KEY_PREFIX+weixinAppConfig.getAppId();
		String accessTokenValue = stringRedisTemplate.opsForValue().get(accessTokenKey);

		if(isAnyCase || StringUtils.isEmpty(accessTokenValue)){
			logger.info("AccessToken过期，开始重新查询:config:{}" ,weixinAppConfig.getAppId());
			String url_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
			String appId = weixinAppConfig.getAppId();
			String secret = weixinAppConfig.getAppSecret();
			OAuthAccessTokenDto accessToken = sendToWx(url_access_token , OAuthAccessTokenDto.class ,  appId , secret);
			if(accessToken == null || StringUtils.isBlank(accessToken.getAccess_token())){
				logger.error("获取AccessToken失败");
				return null;
			}
			
			stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken.getAccess_token(), 7200, TimeUnit.SECONDS);
			return accessTokenValue;
		}
		return accessTokenValue;
	}


	/**
	 * 发送http请求
	 * 
	 * @since 2015年4月23日 上午10:27:02
	 * @param url           http请求的地址
	 * @param paramStr      请求所携带的参数
	 * @return              Map数据，详见接口介绍
	 */
	@Override
	public Map<String , Object> sendHttpRequest(String url,String access_token, String paramStr,String paramType) {
		if(CheckUtils.isEmpty(url)){//请求的地址不能为空
			logger.error("#####发送Http请求失败，请求的地址不能为空#####");
			return null;
		}
		//替换URL中的access_token字段
		url = String.format(url, access_token);
		HttpClient httpClient = new HttpClient();
	    PostMethod method = new PostMethod(url);
	    method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8"); 
	    try {
	    	if(paramStr != null && !paramStr.trim().equals("") && paramType != null && !paramType.trim().equals("")) {
	    		RequestEntity requestEntity = new StringRequestEntity(paramStr,paramType,"UTF-8");
	    		method.setRequestEntity(requestEntity);
	    	}
	    	method.releaseConnection();
	    	int statusCode = httpClient.executeMethod(method);
	    	if(statusCode == HttpStatus.SC_OK){
	    		logger.info("#####发送http请求成功#####");
	    		return JsonMapper.toMap(method.getResponseBodyAsString());//将json字符串转化为map对象
	    	}else{
	    		logger.error("#####发送http请求失败,错误码"+statusCode+"#####");
	    	}
	    } catch (HttpException ex) {
	    	logger.error("#####发送http请求失败，失败原因："+ex.getMessage());
	    	logger.error(ex.getMessage(),ex);
	    } catch (IOException ex) {
	    	logger.error("#####发送http请求失败，失败原因："+ex.getMessage());
	    	logger.error(ex.getMessage(),ex);
	    } 
	    return null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Map<String , Object> sendHttpsRequest(String url , String paramStr){
		try {
			HttpClient httpClient = new HttpClient();  
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(20*1000);//设置超时时间

			Protocol.registerProtocol("https", new Protocol("https", new MySSLProtocolSocketFactory(), 443));
			PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type","application/json;charset=UTF-8");
			postMethod.setRequestBody(paramStr);
//			if(params != null && params.size() > 0){
//				for(Entry<String, String> entry : params.entrySet()){
//					postMethod.setParameter(entry.getKey(), entry.getValue());
//				}
//			}
			int statusCode = httpClient.executeMethod(postMethod);
			//处理响应结果
			InputStream ins = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null){
				sbf.append(line);
			}
			String response = new String(sbf);
			logger.debug("微信正常返回：【{}】" , response);
			
			return JsonMapper.toMap(response);//将json字符串转化为map对象
		} catch (IOException e) {
			logger.error("向微信发送失败" , e);
		}
		return null;
	}

	static class MySSLProtocolSocketFactory implements ProtocolSocketFactory {

		private SSLContext sslcontext = null;

		private SSLContext createSSLContext() {
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("SSL");
				sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
			return sslcontext;
		}

		private SSLContext getSSLContext() {
			if (this.sslcontext == null) {
				this.sslcontext = createSSLContext();
			}
			return this.sslcontext;
		}

		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port);
		}

		public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
			return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
		}

		public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
			if (params == null) {
				throw new IllegalArgumentException("Parameters may not be null");
			}
			int timeout = params.getConnectionTimeout();
			SocketFactory socketfactory = getSSLContext().getSocketFactory();
			if (timeout == 0) {
				return socketfactory.createSocket(host, port, localAddress, localPort);
			} else {
				Socket socket = socketfactory.createSocket();
				SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
				SocketAddress remoteaddr = new InetSocketAddress(host, port);
				socket.bind(localaddr);
				socket.connect(remoteaddr, timeout);
				return socket;
			}
		}

		// 自定义私有类
		private static class TrustAnyTrustManager implements X509TrustManager {

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
		}

	}
	
	public String getCode(String redirectUrl, String scope,WeixinAppConfig weixinAppConfig){
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STATE#wechat_redirect";
		try {
			return String.format(url, weixinAppConfig.getAppId() , URLEncoder.encode(redirectUrl , "UTF-8") , scope);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage() , e);
		}
	}
}
