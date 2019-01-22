package com.qlbs.Bridge.module.youxifan.ios.param;

/**
 * 抽出公共参数 参数抽象类
 * 
 * @auth Jeremy
 * @date 2019年1月9日下午2:24:03
 */
public abstract class AbstractParameter {

	private String userId;
	private String serverId;
	private String qdCode1;
	private String qdCode2;
	private String gameKey;

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getQdCode1() {
		return qdCode1;
	}

	public void setQdCode1(String qdCode1) {
		this.qdCode1 = qdCode1;
	}

	public String getQdCode2() {
		return qdCode2;
	}

	public void setQdCode2(String qdCode2) {
		this.qdCode2 = qdCode2;
	}

}
