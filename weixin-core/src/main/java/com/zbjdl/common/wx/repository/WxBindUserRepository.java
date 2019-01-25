package com.zbjdl.common.wx.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zbjdl.common.respository.mybatis.GenericRepository;
import com.zbjdl.common.wx.entity.WxBindUser;

@Repository("wxBindUserRepository")
public interface WxBindUserRepository extends GenericRepository {
	/**
	 * 根据用户标识和系统编码用户绑定基本信息
	 *
	 * @param userId		用户标识
	 * @param systemCode	系统编码
	 *            
	 * @return 会员基本信息
	 */
	public WxBindUser queryBindUserByUserId(Map<String,Object> paramMap);

	/**
	 * 根据微信OpendId和系统编码用户绑定基本信息
	 * 
	 * @param openId
	 * @param systemCode
	 * @return 会员基本信息
	 */
	public WxBindUser queryBindUserByOpenId(Map<String,Object> paramMap);

	public WxBindUser queryBindUser(Map<String,Object> paramMap);
	
	public void deleteBindUser(Map<String,Object> paramMap);
	
	public WxBindUser queryBindUserByLoginName(Map<String,Object> paramMap);

}
