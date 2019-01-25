package com.zbjdl.common.wx.util.dto;

/**
 * 所有接口调用的父类，用户提供调用失败的统一属性
 * 
 * @since 2014-12-21 11:57:20
 */
public class BaseAPIDto {
	/**
	 * 获取access_token时AppSecret错误，或者access_token无效
	 */
	public static final String ERROR_INVALID = "40001";
	
	/**
	 * access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明
	 */
	public static final String ERROR_ACCESSTOKEN = "42001";
	
	private String errcode;
	private String errmsg;
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
