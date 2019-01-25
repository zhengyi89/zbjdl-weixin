package com.zbjdl.common.wx.manager;

import com.zbjdl.common.wx.entity.WxUser;


/**
 * 微信用户表
 *
 */
public interface WxUserManager {

	/**
	 * 保存用户信息
	 * @param user
	 */
	void save(WxUser user);
	
	/**
	 * 读取用户信息
	 */
	WxUser select(String openId);
}
