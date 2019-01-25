package com.zbjdl.common.wx.util.dto;

import java.io.Serializable;
import java.util.Date;


public class WxBindUserDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 9011479241937894466L;

	/**
	 * 用户标识
	 */
	private String userId;
	
	/**
	 * 微信OpenId
	 */
	private String openId;
	
	/**
	 * 系统编码
	 */
	private String systemCode;

	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 创建日期
	 */
	private Date createdDate;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getSystemCode() {
		return systemCode;
	}


	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
	

}
