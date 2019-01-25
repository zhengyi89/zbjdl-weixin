package com.zbjdl.common.wx.manager;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.zbjdl.common.wechat.dto.WeixinAppConfig;
import com.zbjdl.common.wx.util.dto.WxTemplate;

public interface WxManager {
	
	/**
	 * 将设置好的Map数据转换为JSON字符串
	 * 
	 * @since 2015年4月23日 下午1:53:20
	 * @param paramMap
	 * @return
	 */
	public String preSendMessage(WxTemplate wxTemplate);
	
	/**
	 * 获取Access_token字段
	 * 
	 * @since 2015年4月23日 下午2:20:27
	 * @param isAnyCase           是否每次都重新查询Access_Token，因为每天Access_Token的查询次数有限，所以该值建议设为false
	 * @return                    access_token字段
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getAccessToken(WeixinAppConfig weixinAppConfig,boolean isAnyCase) throws HttpException, IOException;
	
	/**
	 * 发送http请求
	 * 
	 * @since 2015年4月23日 上午10:22:38
	 * @param url         发送http请求的地址
	 * @param paramStr    请求携带的内容
	 * @return            请求返回的内容（Map<String , Object>）
	 */
	public Map<String , Object> sendHttpRequest(String url,String access_token,String paramStr,String paramType);
	
	/**
	 * 发送Https请求
	 * @param url
	 * @param params
	 * @return
	 */
	public Map<String , Object> sendHttpsRequest(String url , String params);
	
	/**
	 * 对详情链接进行必要处理；
	 * 
	 * @since 2015年5月5日 下午3:07:00
	 * @param redirectUrl
	 * @param scope
	 * @return
	 */
	public String getCode(String redirectUrl, String scope,WeixinAppConfig weixinAppConfig);

}
