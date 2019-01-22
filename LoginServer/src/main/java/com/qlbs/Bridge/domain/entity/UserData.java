package com.qlbs.Bridge.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auth Jeremy
 * @date 2018年9月3日下午2:09:01
 */
@Entity
@Table(name = "user_mapping")
public class UserData {

	// 用户id
	@Column(name = "userId")
	private String userId;

	// 玩家id
	@Id
	@Column(name = "identityId", length = 50)
	private String identityId;

	// 服务器id
	@Column(name = "serverId")
	private int serverId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof UserData))
			return false;
		UserData data = (UserData) obj;
		if (data.identityId != getIdentityId())
			return false;
		if (!(data.serverId == getServerId()))
			return false;
		if (!data.userId.equals(getUserId()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return getUserId().hashCode();
	}

	@Override
	public String toString() {
		return userId;
	}
}
