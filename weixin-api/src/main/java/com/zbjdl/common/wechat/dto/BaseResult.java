package com.zbjdl.common.wechat.dto;

import java.io.Serializable;

/**
 * 基础微信数据返回对象
 * 
 * 
 * @since 2015-04-12 15:30:39
 */
public class BaseResult implements Serializable{
	private static final long serialVersionUID = 1L;
	//调用成功
	private static int SUCCESS = 0;
	
	/**
	 * 错误返回码
	 */
	private Integer errcode;
	/**
	 * 错误原因
	 */
	private String errmsg;
	
	/** 
	 * 判断是否为Access_token失效
	 * @return
	 */
	public boolean isAccessTokenOutOfTime(){
		//accessToken超时或者无效
		return 42001 == errcode || 40001 == errcode;
	}
	
	/**
	 * 判断处理是否成功
	 * @return
	 */
	public boolean isSuccess(){
		return errcode != null && SUCCESS == errcode;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
