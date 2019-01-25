/**
 * 
 */
package com.zbjdl.common.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.wechat.dto.WeixinAppConfig;
import com.zbjdl.common.wx.manager.WxManager;
import com.zbjdl.common.wx.service.WeixinService;
import com.zbjdl.common.wx.util.dto.BaseAPIDto;
import com.zbjdl.common.wx.util.dto.WxTemplate;

/**
 * @author yejiyong
 *
 */
@Service("weixinService")
public class WeixinServiceImpl implements WeixinService{
	private final static Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);
	private String WX_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s"; 

	

	@Autowired
	private WxManager wxManager ;
	/**
	 * 向用户发送模板消息
	 * @param messageJson 模板消息体
	 */
	@Override
	public Map<String,String> sendTemplateMessage(WeixinAppConfig weixinAppConfig,WxTemplate wxTemplate) {
		logger.info("*****向微信发送模板消息开始*****");
		Map<String,String> returnMap = null;
		String error = null;
		try{
			wxTemplate.setUrl(wxManager.getCode(wxTemplate.getUrl(),"snsapi_userinfo",weixinAppConfig));//进行编码);
			//首先将Map数据转换为JSON字符串
			String messageJson = wxManager.preSendMessage(wxTemplate);
			//替换地址中的access_token字段
			String accessToken = wxManager.getAccessToken(weixinAppConfig,false);
			String url = String.format(WX_TEMPLATE_MESSAGE_URL, accessToken);
			Map<String,Object> resultMap = wxManager.sendHttpsRequest(url, messageJson);
			if(resultMap != null){
				String code = resultMap.get("errcode").toString();
				logger.info("send Template Message errcode is {}",code);
				if("0".equals(code)){
					//发送正常
					returnMap = new HashMap<String,String>();
					returnMap.put("msgid", resultMap.get("msgid").toString());//消息id
					returnMap.put("errmsg", "ok");//结果信息
					return returnMap;
				} else if(BaseAPIDto.ERROR_INVALID.equals(code) || BaseAPIDto.ERROR_ACCESSTOKEN.equals(code)){
					//access_token过期或者无效，需要重新获取并发送
					String accessToken_again = wxManager.getAccessToken(weixinAppConfig,true);
					String tempUrl = String.format(WX_TEMPLATE_MESSAGE_URL, accessToken_again);
					Map<String,Object> tempMap = wxManager.sendHttpsRequest(tempUrl, messageJson);
					returnMap = new HashMap<String,String>();
					returnMap.put("msgid", CheckUtils.isEmpty(tempMap.get("msgid").toString())==false?tempMap.get("msgid").toString():"");//消息id
					returnMap.put("errmsg",tempMap.get("errmsg").toString());//结果信息
					return returnMap;
				} else{
					logger.error("#####发送模板消息失败，失败原因："+resultMap.get("errmsg")+"#####");
					returnMap = new HashMap<String,String>();
					returnMap.put("msgid", "");//消息id
					returnMap.put("errmsg",resultMap.get("errmsg").toString());//结果信息
					return returnMap;
				}
			}
		}catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			error = ex.getMessage();
			logger.error("#####发送模板消息失败，失败原因：发送返回字段转换为JSON对象失败#####");
		}
		logger.info("*****向微信发送模板消息结束*****");
		returnMap = new HashMap<String,String>();
		returnMap.put("msgid", "");//消息id
		returnMap.put("errmsg",error);//结果信息
		return returnMap;
	}
	

}
