package com.zbjdl.common.wechat.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.wechat.dto.user.UserWeiXin;
/**
 * 微信关注用户管理
 * 
 * @since 2015-04-21 14:02:45
 */
public class UserService extends BaseService{
	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	// 用户类：获取用户详细信息
	public static String url_userinfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
	

	/**
	 * 获取用户丰富信息(UnionID机制)，需要用户已经关注服务号
	 * @param openId 微信用户OpenId
	 */
	public static UserWeiXin getUserInfoByUnionId(String openId){
		try {
			LOGGER.info("获取用户详细信息(unionId)，OpenId:{}" , openId);
			//全局AccessToken机制：超时则重新获取，否则既使用
			UserWeiXin dto = sendToWxWithAccessToken(url_userinfo, UserWeiXin.class , openId);
			return dto;
		} catch (Exception e){
			LOGGER.error("获取用户详细信息失败" , e);
		}
		return null;
	}

}
