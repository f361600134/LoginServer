package com.qlbs.Bridge.common.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.qlbs.Bridge.util.CommonUtil;

/**
 * 工程自定义全局配置, 使用静态注入
 * 
 * 自定义配置加入渠道信息配置,渠道号:渠道信息(登录密匙, 渠道名称)
 * 
 * @change 简化了自定义配置的加载和使用
 * @notice 不论加或是不加static关键字都会成功, 都可以静态注入成功
 * @note 使用静态注入Map,不能成功, Set, list可以成功注入, 找不到原因.2018.9.21 最后发现只有Map不能静态注入
 * @auth Jeremy
 * @date 2018年9月20日下午2:52:15
 */
@Component
@ConfigurationProperties(prefix = "define")
public class DefineConfig {

	/**
	 * 管理服务器地址
	 */
	public static String serverMgrUrl;

	/**
	 * 游戏gameKey
	 */
	public static Map<String, String> gameKeyMap;

	/**
	 * key: channelId <br>
	 * value: 自定义渠道名字
	 */
	public static Map<String, String> channelInfoMap;

	public void setServerMgrUrl(String serverMgrUrl) {
		DefineConfig.serverMgrUrl = serverMgrUrl;
	}

	/**
	 * 无法自动注入map, 把gameKeyMap当做string来注入, 然后转成map
	 * 
	 * @note 官方给的demo是支持注入Map, 但是没有成功. TODO 有空找解决方案
	 * @param gameKeyMap
	 * @return void
	 * @date 2018年9月21日下午6:10:54
	 */
	public void setGameKeyMap(String gameKeyMapStr) {
		DefineConfig.gameKeyMap = CommonUtil.strToMap(gameKeyMapStr, HashMap.class);
	}

	/**
	 * 静态注入渠道商信息, 用于拼接组装成账号id
	 * 
	 * @param channelInfoMapStr
	 * @return void
	 * @date 2018年12月3日上午11:38:55
	 */
	public void setChannelInfoMap(String channelInfoMapStr) {
		DefineConfig.channelInfoMap = CommonUtil.strToMap(channelInfoMapStr, HashMap.class);
	}

}
