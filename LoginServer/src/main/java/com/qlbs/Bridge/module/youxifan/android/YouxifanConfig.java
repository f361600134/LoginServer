
package com.qlbs.Bridge.module.youxifan.android;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * There are two ways to analyze your define properties.
 * 
 * 1. Adds the #@Component #@ConfigurationProperties(prefix = "") on the top of
 * class.
 * 
 * 2. Adds the #@Value("${youxifan.gamekey}") on the variables.
 * 
 * I can't inject to map in spring boot, so I use #CommonUtil.strToMap to
 * complete it.
 * 
 * @see https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-
 *      Configuration-Binding
 * @auth Jeremy
 * @date 2018年9月10日上午10:30:19
 */
@Component
@ConfigurationProperties(prefix = "youxifan")
@PropertySource("classpath:config/sdk/youxifan.properties")
public class YouxifanConfig {

	public static String appkey;

	public void setAppkey(String appkey) {
		YouxifanConfig.appkey = appkey;
	}

}
