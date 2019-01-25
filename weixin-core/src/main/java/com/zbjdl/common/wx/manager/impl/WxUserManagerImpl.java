package com.zbjdl.common.wx.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.wx.entity.WxUser;
import com.zbjdl.common.wx.manager.WxUserManager;
import com.zbjdl.common.wx.repository.WxUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("wxUserManager")
public class WxUserManagerImpl implements WxUserManager {
	private Logger logger = LoggerFactory.getLogger(WxUserManagerImpl.class);
	@Resource
	private WxUserRepository wxUserRepository;
	
	@Override
	public void save(WxUser user) {
		if(StringUtils.isBlank(user.getOpenId())){
			return;
		}
		WxUser entity = select(user.getOpenId());
		if(entity == null){
			wxUserRepository.save(user);
		} else {
			String nickname = user.getNickName();
			if(StringUtils.isNotBlank(nickname)){
				entity.setNickName(user.getNickName());
			}
			String photoUrl = user.getPhotoUrl();
			if(StringUtils.isNotBlank(photoUrl)){
				entity.setPhotoUrl(user.getPhotoUrl());
			}
			if(StringUtils.isNotBlank(nickname) || StringUtils.isNotBlank(photoUrl)){
				wxUserRepository.update(entity);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public WxUser select(String openId) {
		if(StringUtils.isBlank(openId)){
			return null;
		}
		List<WxUser> users = wxUserRepository.findList(openId);

		if(users == null || users.size() == 0){
			return null;
		} else if (users.size() > 1){
			logger.error("系统业务数据错误，不予查询'");
			return null;
		} else {
			return users.get(0);
		}
	}

}
