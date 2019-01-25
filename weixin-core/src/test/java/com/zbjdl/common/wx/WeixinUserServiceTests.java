package com.zbjdl.common.wx;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zbjdl.common.wx.param.WxUserDTO;
import com.zbjdl.common.wx.service.WeixinUserService;
import com.zbjdl.common.wx.util.dto.WxBindUserDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MchserviceWxCoreApplication.class)
@WebAppConfiguration
public class WeixinUserServiceTests {

	@Autowired
	WeixinUserService weixinUserService;
	@Test
	public void testBind() {
		String openId="123456";
		String userId="NO1234";
		String systemCode="T0";
		WxBindUserDto bindUser = new WxBindUserDto();
		bindUser.setOpenId(openId);
		bindUser.setUserId(userId);
		bindUser.setSystemCode(systemCode);
		bindUser.setLoginName("13888888888");
		weixinUserService.bind(bindUser);
		
		
	}
	@Test
	public void testUnBind() {
		String openId="123456";
		String userId="NO1234";
		String systemCode="T0";
		weixinUserService.unBind(userId, openId, systemCode);
		
		
	}
	
	@Test
	public void testIsBind(){
		String openId="123456";
		String systemCode="T0";
		Boolean flag = weixinUserService.isBind(openId, systemCode);
		
		Assert.assertTrue(flag);
		flag = weixinUserService.isBind("1234567", systemCode);
		
		Assert.assertFalse(flag);
	}
	@Test
	public void testBindWxInfo() {
		String openId="123456";
		WxUserDTO userDTO = new WxUserDTO();
		userDTO.setPhotoUrl("ssssss");
		userDTO.setNickName("yyyyyy");
		userDTO.setOpenId(openId);

		weixinUserService.bindWxInfo(userDTO);
		
	}
}
