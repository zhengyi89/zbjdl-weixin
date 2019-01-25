package com.zbjdl.common.wx.util.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信模板消息
 * 
 * @since 2015年4月23日 下午6:52:50
 */
public class WxTemplate implements Serializable{  
    /**
	 * 
	 * @since 2015年4月23日 下午7:10:43
	 */
	private static final long serialVersionUID = 1670421396270824636L;
	
	/**
	 * 模板ID
	 */
	private String template_id;
	
	/**
	 * 发送对象的OPEN_ID
	 */
    private String touser;
    
    /**
     * 详情的链接地址
     */
    private String url;  
    
    /**
     * 顶部的字体颜色
     */
    private String topcolor;  
    
    /**
     * 模板消息中的子信息
     */
    private Map<String,TemplateData> data;  
      
    public String getTemplate_id() {  
        return template_id;  
    }  
    public void setTemplate_id(String template_id) {  
        this.template_id = template_id;  
    }  
    public String getTouser() {  
        return touser;  
    }  
    public void setTouser(String touser) {  
        this.touser = touser;  
    }  
    public String getUrl() {  
        return url;  
    }  
    public void setUrl(String url) {  
        this.url = url;  
    }  
    public String getTopcolor() {  
        return topcolor;  
    }  
    public void setTopcolor(String topcolor) {  
        this.topcolor = topcolor;  
    }  
    public Map<String,TemplateData> getData() {  
        return data;  
    }  
    public void setData(Map<String,TemplateData> data) {  
        this.data = data;  
    }  
}  