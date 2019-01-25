package com.zbjdl.common.wx.entity;


import com.zbjdl.common.respository.entity.PersistenceEntity;
import com.zbjdl.common.utils.StringUtils;


/**
 * 微信活动抽象实体
 * 
 * 
 */
public class WxBindUser extends PersistenceEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 微信OpenId
	 */
	private String openId;
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 系统编码
	 */
	private String systemCode;

	
	
	public WxBindUser() {
		super();
	}

	public WxBindUser(String openId) {
		this.openId = openId;
	}
		
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


}
