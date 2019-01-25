package com.zbjdl.common.wx.util.dto;

import java.io.Serializable;

/**
 * 微信模板消息中的数据
 * 
 * @since 2015年4月23日 下午6:53:11
 */
public class TemplateData implements Serializable{
	/**
	 * 
	 * @since 2015年4月23日 下午7:12:25
	 */
	private static final long serialVersionUID = -7243516472655612715L;
	
	/**
	 * 模板消息子信息的内容
	 */
	private String value; 
	
	/**
	 * 模板消息子信息的内容的字体颜色
	 */
	private String color;
	
	public String getValue() {  
		return value;  
	}  
    public void setValue(String value) {  
        this.value = value;  
    }  
    public String getColor() {  
        return color;  
    }  
    public void setColor(String color) {  
        this.color = color;  
    }  
}
