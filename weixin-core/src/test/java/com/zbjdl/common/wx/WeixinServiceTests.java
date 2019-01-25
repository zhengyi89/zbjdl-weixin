package com.zbjdl.common.wx;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zbjdl.common.wechat.dto.WeixinAppConfig;
import com.zbjdl.common.wx.service.WeixinService;
import com.zbjdl.common.wx.util.dto.TemplateData;
import com.zbjdl.common.wx.util.dto.WxTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MchserviceWxCoreApplication.class)
@WebAppConfiguration
public class WeixinServiceTests {

	@Autowired
	WeixinService weixinService;

	@Test
	public void testSendTemplateMessage() {

		String openId = "oW3ODv0unDkkFruT72k1xBMDUgTU";
		
		WeixinAppConfig weixinAppConfig = new WeixinAppConfig();
		String appId = "wx7be5739fc0f37b57";
		String appSecret = "43a69593df646ac6fc164ec3d8059b44";
		weixinAppConfig.setAppId(appId);
		weixinAppConfig.setAppSecret(appSecret);
		
		WxTemplate wxTemplate = new WxTemplate();
		
		//根据申请记录ID查询申请信息
		//TODO 模板ID
		wxTemplate.setTemplate_id("fnSlLrQPnwdUHae-7rbFmWkdTd3vhyVWUHDweDVNtbQ");
		wxTemplate.setTopcolor("#000000");
		wxTemplate.setTouser(openId);
					
		//TODO 
		wxTemplate.setUrl("http://www.yunpal.cn");
		
		Map<String,TemplateData> data = new HashMap<String,TemplateData>();
		
		//介绍
		TemplateData firstData = new TemplateData();
		firstData.setValue("尊敬的用户，您办理的"+"YYYY"+"赎楼申请，申请编号："+"xxxxxx ,有新进展。");
		firstData.setColor("#000000");
		
		//用户姓名
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue("业务员A");
		keyword1.setColor("#000000");
		
		//业务申请类型
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue("风控复审");
		keyword2.setColor("#000000");
		
		//处理结果
		TemplateData keyword3 = new TemplateData();
		keyword3.setColor("#000000");
		//错误信息
		TemplateData remark = new TemplateData();
		remark.setColor("#000000");
		//审核通过
		keyword3.setValue("已完成");
		remark.setValue("祝您生活愉快！");
		//处理时间
		TemplateData keyword4 = new TemplateData();
		//TODO
		keyword4.setValue("2016年06月12日");
		keyword4.setColor("#000000");
				
		data.put("first", firstData);
	    data.put("keyword1", keyword1);
	    data.put("keyword2", keyword2);
	    data.put("keyword3", keyword3);
	    data.put("keyword4", keyword4);
	    data.put("remark", remark);
	    
	    wxTemplate.setData(data);
		weixinService.sendTemplateMessage(weixinAppConfig , wxTemplate );
		
	}
}
