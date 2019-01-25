package com.zbjdl.common.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:/wx-spring/applicationContext-provider.xml")

public class WxWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxWebApplication.class, args);
	}
}
