package com.zbjdl.common.wechat.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.wechat.cache.WxCacheEnum;
import com.zbjdl.common.wechat.dto.js_sdk.SignDto;
import com.zbjdl.common.wechat.dto.js_sdk.JsTicketDto;
import com.zbjdl.common.wechat.util.SignUtil;
import com.zbjdl.common.wx.util.ConfigEnum;
import com.zbjdl.common.wx.util.ConfigUtils;

/**
 * 系统对接JS-SDK提供服务接口
 * 
 * @since 2015-04-12 14:37:44
 */
public class JsApiService extends BaseService{
	private static Logger LOGGER = LoggerFactory.getLogger(JsApiService.class);

	//JS-SDK：获取临时凭证，有效期7200秒
	public static String URL_JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";


	/**
	 * 获取JS-SDK的jsapi_ticket
	 * @throws IOException
	 * @throws HttpException
	 */
	private static String getTicket() throws IOException{
		String ticket = WxCacheEnum.JSAPI_TICKET.getCache();
		if(StringUtils.isBlank(ticket)){
			JsTicketDto ticketDto = sendToWxWithAccessToken(URL_JS_TICKET, JsTicketDto.class);
			if(ticketDto != null && ticketDto.isSuccess()){
				LOGGER.info("获取临时票据成功:{}" , ticketDto.getTicket());
				WxCacheEnum.JSAPI_TICKET.cache(ticketDto.getTicket());
				return ticketDto.getTicket();
			} else {
				LOGGER.error("获取临时票据失败：{}," , ticketDto , ticket);
				return null;
			}
		} else {
			return ticket;
		}
	}

	/**
	 * JS-SDK签名算法
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static SignDto sign(String url) {
		try {
			SignDto sign = new SignDto();
			String ticket = getTicket();
			String nonceStr = SignUtil.create_nonce_str();
			String timestamp = SignUtil.create_timestamp();
			// 注意这里参数名必须全部小写，且必须有序
			String string1 = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s", ticket , nonceStr , timestamp , url);
			String signature = SignUtil.sha1Digest(string1, "UTF-8");
			sign.setSignature(signature);
			sign.setNonceStr(nonceStr);
			sign.setTimestamp(timestamp);
			sign.setAppId((String)ConfigUtils.getAppConfigParam(ConfigEnum.WX_APPID));
			// LOGGER.info(signature);
			// LOGGER.info(nonceStr);
			// LOGGER.info(timestamp);
			// LOGGER.info(sign.getAppId());

			return sign;
		} catch (Exception e){
			LOGGER.error("JsApi sign error" , e);
			return null;
		}
	}


}
