/**
 * 
 */
package com.zbjdl.common.wx.enumtype;

import java.io.Serializable;

/**
 * 枚举需要实现的行为
 * 
 *
 */
public interface EnumBehavior extends Serializable{

	/**
	 * 获得枚举的中文解释
	 * @return
	 */
	public String getCn();
	
	/**
	 * 取得枚举的索引
	 * @return
	 */
	public int getIndex();
	
	/**
	 * 重写枚举的ordinal方法
	 * @return
	 */
	public int ordinal();
}
