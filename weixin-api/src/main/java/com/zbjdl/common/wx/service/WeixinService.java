package com.zbjdl.common.wx.service;

import java.util.Map;

import com.zbjdl.common.wechat.dto.WeixinAppConfig;
import com.zbjdl.common.wx.util.dto.WxTemplate;

/**
 * 微信公用服务
 * 支持多个工作号
 * @author yejiyong
 *
 */
public interface WeixinService {
	

	/**
	 * 发送微信模板消息
	 * @param weixinConfigCode	微信公众平台配置编码
	 * 							基于此编码，能够获取到该公众号相关的配置信息	
	 * @param wxTemplate    模板消息体
	 * @return Map          发送正常时msgid不为空，errmsg='ok';发送失败时msgid为空("")，errmsg=错误信息
	 * key        value
	 * msgid      消息id
	 * errmsg     错误消息
	 */
	public Map<String,String> sendTemplateMessage(WeixinAppConfig weixinAppConfig,WxTemplate wxTemplate);
}
