package com.qlbs.Bridge.common.result.support;

import com.google.common.base.Preconditions;
import com.qlbs.Bridge.common.config.DefineConfig;
import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.domain.entity.UserData;
import com.qlbs.Bridge.util.MD5;
import com.qlbs.Bridge.util.UrlUtil;

/**
 * 用户认证失败，返回结果对象, 通用返回结果集
 */
public class AuthResult extends AbstractResult {

	private String userId;
	private String userName;
	private String identityName;
	private String identityId;
	private int serverId;
	private long tstamp;
	private String sign;

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getIdentityName() {
		return identityName;
	}

	public String getIdentityId() {
		return identityId;
	}

	public int getServerId() {
		return serverId;
	}

	public long getTstamp() {
		return tstamp;
	}

	public String getSign() {
		return sign;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public void setTstamp(long tstamp) {
		this.tstamp = tstamp;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public AuthResult(ErrorCodeEnum codeEnum) {
		super(codeEnum);
	}

	public AuthResult(ErrorCodeEnum codeEnum, String userId, String userName, String identityName, String identityId, int serverId, long tstamp, String sign) {
		super(codeEnum);
		this.userId = userId;
		this.userName = userName;
		this.identityName = identityName;
		this.identityId = identityId;
		this.serverId = serverId;
		this.tstamp = tstamp;
		this.sign = sign;
	}

	/**
	 * 构造成功的返回信息
	 * 
	 * @return
	 */
	public static AuthResult success(UserData userData, String username, String gameKey) {
		AuthResult result = new AuthResult(ErrorCodeEnum.SUCCESS);
		long curTime = System.currentTimeMillis();
		String sign = MD5.digest(userData.getIdentityId() + curTime + DefineConfig.gameKeyMap.get(gameKey));
		// 组装其他信息
		result.setSign(sign);
		result.setIdentityName(UrlUtil.utf8Encode(username));
		result.setUserName(UrlUtil.utf8Encode(username));
		result.setUserId(userData.getUserId());
		result.setServerId(Integer.valueOf(userData.getServerId()));
		result.setIdentityId(userData.getIdentityId());
		result.setTstamp(curTime);
		return result;
	}

	/**
	 * 构造失败的返回信息
	 * 
	 * @return
	 */
	public static AuthResult faild(ErrorCodeEnum codeEnum) {
		// 错误提示
		Preconditions.checkNotNull(codeEnum);
		Preconditions.checkArgument(!(codeEnum == ErrorCodeEnum.SUCCESS), "错误码有误,codeEnum:" + codeEnum.getStatus());
		return new AuthResult(codeEnum);
	}

	@Override
	public String toString() {
		return "AuthResult [code=" + super.getCode() + ", desc=" + super.getDesc() + ", identityName=" + identityName + ", identityId=" + identityId + ", serverId=" + serverId + ", tstamp=" + tstamp + ", sign=" + sign + "]";
	}

}
