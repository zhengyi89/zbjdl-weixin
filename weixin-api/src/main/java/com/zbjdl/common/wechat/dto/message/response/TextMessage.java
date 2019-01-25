package com.zbjdl.common.wechat.dto.message.response;


/**
 * 文本消息
 * 
 * @since 2015-01-10 17:13:42
 */
public class TextMessage {
	/**
	 * 接收方帐号（收到的OpenID）
	 */
	private String ToUserName;
	/**
	 * 开发者微信号
	 */
	private String FromUserName;
	/**
	 * 消息内容
	 */
	private String text;
	
	
	String xml = "<xml>"+
			"<ToUserName><![CDATA[%s]]></ToUserName>"+
			"<FromUserName><![CDATA[%s]]></FromUserName>"+
			"<CreateTime>%s</CreateTime>"+
			"<MsgType><![CDATA[text]]></MsgType>"+
			"<Content><![CDATA[%s]]></Content>"+
		"</xml> ";

	public TextMessage() {
	}
	public TextMessage(String toUserName, String fromUserName, String text) {
		super();
		this.ToUserName = toUserName;
		this.FromUserName = fromUserName;
		this.text = text;
	}
	@Override
	public String toString() {
		return String.format(xml, ToUserName , FromUserName , System.currentTimeMillis() , text);
	}
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
