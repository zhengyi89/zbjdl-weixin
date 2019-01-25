package com.zbjdl.common.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * Mybatis配置类
 *
 */
@Configuration
@MapperScan(basePackages="com.zbjdl.common.wx.repository")
public class MybatisConfiguration {

}
