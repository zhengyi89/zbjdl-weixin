package com.zbjdl.common.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zbjdl.common.utils.BeanUtils;
import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.wx.entity.WxBindUser;
import com.zbjdl.common.wx.entity.WxUser;
import com.zbjdl.common.wx.manager.WxUserManager;
import com.zbjdl.common.wx.param.WxUserDTO;
import com.zbjdl.common.wx.repository.WxBindUserRepository;
import com.zbjdl.common.wx.repository.WxUserRepository;
import com.zbjdl.common.wx.service.WeixinUserService;
import com.zbjdl.common.wx.util.dto.WxBindUserDto;
@Component("weixinUserService")
public class WeixinUserServiceImpl implements WeixinUserService {
	private Logger logger = LoggerFactory.getLogger("weixinUserService");
	@Autowired
	private WxUserManager wxUserManager;
	@Autowired
	private WxBindUserRepository wxBindUserRepository;
	@Resource
	private WxUserRepository wxUserRepository;
	@Override
	public void bind(WxBindUserDto wxBindUserDto) {
		CheckUtils.notEmpty(wxBindUserDto.getUserId(), "userId不能为空");
		CheckUtils.notEmpty(wxBindUserDto.getSystemCode(), "systemCode不能为空");
		CheckUtils.notEmpty(wxBindUserDto.getOpenId(), "OpenId不能为空");

		WxBindUserDto entity = queryBindUserByUserId(wxBindUserDto.getUserId(),wxBindUserDto.getSystemCode());
		if (entity != null){
			if (StringUtils.isNotBlank(entity.getOpenId()) && !wxBindUserDto.getOpenId().equals(entity.getOpenId())) {
				throw new RuntimeException("微信已经被其它账号绑定");
			}			
		}else{
			WxBindUserDto wxMember = queryBindUserByOpenId(wxBindUserDto.getOpenId(),wxBindUserDto.getSystemCode());
			if (wxMember == null) {
				WxBindUser bindUser = new WxBindUser();
				bindUser.setOpenId(wxBindUserDto.getOpenId());
				bindUser.setUserId(wxBindUserDto.getUserId());
				bindUser.setSystemCode(wxBindUserDto.getSystemCode());
				bindUser.setLoginName(wxBindUserDto.getLoginName());
				
				wxBindUserRepository.save(bindUser);
				
			} else if (!wxMember.getUserId().equals(wxBindUserDto.getUserId())) {
				throw new RuntimeException("微信已经被其它账号绑定");
			}			
		}



	}

	@Override
	public void unBind(String userId, String openId, String systemCode) {
		CheckUtils.notEmpty(userId, "userId不能为空");
		CheckUtils.notEmpty(systemCode, "systemCode不能为空");
		CheckUtils.notEmpty(openId, "OpenId不能为空");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("systemCode", systemCode);
		paramMap.put("openId", openId);

		wxBindUserRepository.deleteBindUser(paramMap);
	}

	@Override
	public boolean isBind(String openId, String systemCode) {
		WxBindUserDto bindUser = queryBindUserByOpenId(openId, systemCode);
		return (bindUser != null);
	}

	@Override
	public WxUserDTO findWxUser(String openId) {
		WxUser user = wxUserManager.select(openId);
		if(user == null){
			return null;
		}
		WxUserDTO dto  = new WxUserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	@Override
	public WxBindUserDto queryBindUserByUserId(String userId, String systemCode) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("systemCode", systemCode);
		WxBindUser bindUser = wxBindUserRepository.queryBindUserByUserId(paramMap);
		if (bindUser == null) {
			return null;
		}
		WxBindUserDto bindUserDto = new WxBindUserDto();
		BeanUtils.copyProperties(bindUser,bindUserDto);

		return bindUserDto;
	}

	@Override
	public WxBindUserDto queryBindUserByOpenId(String openId, String systemCode) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("systemCode", systemCode);
		paramMap.put("openId", openId);

		WxBindUser bindUser = wxBindUserRepository.queryBindUserByOpenId(paramMap);
		if (bindUser == null) {
			return null;
		}
		WxBindUserDto bindUserDto = new WxBindUserDto();
		BeanUtils.copyProperties(bindUser,bindUserDto);
		return bindUserDto;	
		}
	@Override
	public boolean bindWxInfo(WxUserDTO wxInfo){
		String openId = wxInfo.getOpenId();
		String wxPhotoUrl = wxInfo.getPhotoUrl();
		String wxNickName = wxInfo.getNickName();
		CheckUtils.notEmpty(openId, "openId");
		WxUser user = wxUserManager.select(openId);
		if(user == null){
			user = new WxUser();
			BeanUtils.copyProperties(wxInfo, user);
			wxUserManager.save(user);
			return true;
		}
		boolean sw = false;
		//微信头像不为空，并且不等于原来的微信头像
		if(StringUtils.isNotBlank(wxPhotoUrl) && !wxPhotoUrl.equals(user.getPhotoUrl())){
			user.setPhotoUrl(wxPhotoUrl);
			sw = true;
		}
		//微信昵称不为空，并且不等于原来的微信昵称
		if(StringUtils.isNotBlank(wxNickName) && !wxNickName.equals(user.getNickName())){
			logger.info(wxNickName);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < wxNickName.length(); i++) {
			    char ch = wxNickName.charAt(i);
			    if (!Character.isHighSurrogate(ch) && !Character.isLowSurrogate(ch)) {
			        sb.append(ch);
			    }
			}
			wxNickName = sb.toString();
			logger.info(wxNickName);

			
			user.setNickName(wxNickName);
			sw = true;
		}
		if(sw){
			wxUserRepository.update(user);
		}
		return sw;
	}
	
	
	@Override
	public void share(String openId){
		WxUser user = wxUserManager.select(openId);
		if(user == null){
			user = new WxUser(openId);
			wxUserManager.save(user);
		}
		user.addArticleShareCount();
		wxUserRepository.update(user);
	}

	@Override
	public WxBindUserDto queryBindUserByLoginName(String loginName, String systemCode) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		paramMap.put("systemCode", systemCode);

		WxBindUser bindUser = wxBindUserRepository.queryBindUserByLoginName(paramMap);
		if (bindUser == null) {
			return null;
		}
		WxBindUserDto bindUserDto = new WxBindUserDto();
		BeanUtils.copyProperties(bindUser,bindUserDto);
		return bindUserDto;	
	}

}
