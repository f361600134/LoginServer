package com.qlbs.Bridge.module.quick;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 渠道参数如果做成通过Map结构的话, CP方需要一个唯一Key进行获取参数, 但是这个唯一Key不可控.<br>
 * 充值支付的时候需要拿到运营参数, 但是充值回调是渠道方调用, 参数未知. 有些需要先通过渠道参数解码,然后才能获取到详细内容.<br>
 * 对于CP方.<br>
 * 1. 不做统一, 渠道参数单独处理, 能使用多渠道配置的做多渠道配置. 不能做则不做. <br>
 * 2. 作统一, 一个工程则只配置1个运营参数, 如果有多个运营参数, 则重新配置, 程序以最新的配置为准
 * 
 * @auth Jeremy
 * @date 2019年1月2日上午11:24:43
 */
@Component
@ConfigurationProperties(prefix = "quick")
@PropertySource("classpath:config/sdk/quick.properties")
public class QuickConfig {

	public static String productCode;
	public static String callbackKey;
	public static String test;
	public static String md5Key;
	public static String productKey;// 服务器不用
	public static String requestUrl;

	public static void setProductCode(String productCode) {
		QuickConfig.productCode = productCode;
	}

	public static void setCallbackKey(String callbackKey) {
		QuickConfig.callbackKey = callbackKey;
	}

	public static void setTest(String test) {
		QuickConfig.test = test;
	}

	public static void setMd5Key(String md5Key) {
		QuickConfig.md5Key = md5Key;
	}

	public static void setProductKey(String productKey) {
		QuickConfig.productKey = productKey;
	}

	public static void setRequestUrl(String requestUrl) {
		QuickConfig.requestUrl = requestUrl;
	}

	// /**
	// * key: 渠道 value: 渠道下得参数
	// */
	// private Map<String, QuickParam> paramMap;
	//
	// public Map<String, QuickParam> getParamMap() {
	// return paramMap;
	// }
	//
	// public void setParamMap(Map<String, QuickParam> paramMap) {
	// this.paramMap = paramMap;
	// }
	//
	// public QuickParam getParam(String productCode) {
	// return paramMap.get(productCode);
	// }

}
