package com.zbjdl.common.wechat.dto.js_sdk;

import java.io.Serializable;


/**
 * 微信JS-SDK签名数据传输对象
 * 
 * 
 * @since 2015-04-12 14:55:34
 */
public class SignDto implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 微信AppId
	 */
	private String appId;
	
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 签名
	 */
	private String signature;
	
	
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
