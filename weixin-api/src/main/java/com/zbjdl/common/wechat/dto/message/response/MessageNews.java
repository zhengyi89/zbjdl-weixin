package com.zbjdl.common.wechat.dto.message.response;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.common.wechat.dto.BaseResult;

/**
 * 图文消息
 * 
 * @since 2015-01-10 17:13:42
 */
public class MessageNews extends BaseResult{
	private static final long serialVersionUID = 1L;
	public String xml = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>%d</ArticleCount><Articles>%s</Articles></xml> ";

	
	/**
	 * 接收方帐号（收到的OpenID）
	 */
	private String ToUserName;
	/**
	 * 开发者微信号
	 */
	private String FromUserName;
	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 */
	private List<Article> Articles;
	
	
	public MessageNews() {
	}
	public MessageNews(String toUserName, String fromUserName, List<Article> articles) {
		super();
		this.ToUserName = toUserName;
		this.FromUserName = fromUserName;
		this.Articles = articles;
	}
	@Override
	public String toString() {
		StringBuffer articles = new StringBuffer();
		for(Article a : Articles){
			articles.append(a);
		}
		return String.format(xml, ToUserName , FromUserName , System.currentTimeMillis() , Articles.size() , articles);
	}

	/**
	 * 单条图文消息对象
	 * 
	 * @since 2015-01-10 18:10:36
	 */
	public static class Article{
		//标题
		private String Title;
		//描述
		private String Description;
		//图片
		private String PicUrl;
		//原文地址
		private String Url;
		
		String article = "<item>"+
				"<Title><![CDATA[%s]]></Title>"+ 
				"<Description><![CDATA[%s]]></Description>"+
				"<PicUrl><![CDATA[%s]]></PicUrl>"+
				"<Url><![CDATA[%s]]></Url>"+
			"</item>";
		
		
		public Article() {
		}
		public Article(String title, String description, String picUrl, String url) {
			this.Title = title;
			this.Description = description;
			this.PicUrl = picUrl;
			this.Url = url;
		}
		@Override
		public String toString() {
			return String.format(article, Title , Description , PicUrl , Url);
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getDescription() {
			return Description;
		}
		public void setDescription(String description) {
			Description = description;
		}
		public String getPicUrl() {
			return PicUrl;
		}
		public void setPicUrl(String picUrl) {
			PicUrl = picUrl;
		}
		public String getUrl() {
			return Url;
		}
		public void setUrl(String url) {
			Url = url;
		}
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
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
