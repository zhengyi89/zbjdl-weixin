package com.zbjdl.common.wx.service;

import com.zbjdl.common.wx.param.WxUserDTO;
import com.zbjdl.common.wx.util.dto.WxBindUserDto;

public interface WeixinUserService {

	public void bind(WxBindUserDto bindUserDto);

	public void unBind(String userId, String openId, String systemCode);

	/**
	 * 查询指定OpendId是否绑定用户
	 * @param openId
	 * @param systemCode
	 * @return
	 */
	public boolean isBind(String openId, String systemCode);

	public WxUserDTO findWxUser(String openId);

	/**
	 * 根据用户标识和系统编码用户绑定基本信息
	 *
	 * @param userId		用户标识
	 * @param systemCode	系统编码
	 *            
	 * @return 会员基本信息
	 */
	public WxBindUserDto queryBindUserByUserId(String userId,String systemCode);

	/**
	 * 根据微信OpendId和系统编码用户绑定基本信息
	 * 
	 * @param openId
	 * @param systemCode
	 * @return 会员基本信息
	 */
	public WxBindUserDto queryBindUserByOpenId(String openId,String systemCode);
	/**
	 * 绑定用户微信详情
	 */
	boolean bindWxInfo(WxUserDTO info);
	

	
	/**
	 * 微信用户分享
	 */
	void share(String openId);
	
	/**
	 * 根据用户名和系统编码用户绑定基本信息
	 * @param loginName
	 * @param systemCode
	 * @return
	 */
	public WxBindUserDto queryBindUserByLoginName(String loginName, String systemCode);
}
