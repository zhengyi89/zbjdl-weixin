package com.zbjdl.common.wechat.dto;


/**
 * 微信公众平台账号配置信息
 * 
 */
public class WeixinAppConfig extends BaseResult{
	private static final long serialVersionUID = 1L;
	private String appId;
	private String appSecret;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
