package com.qlbs.Bridge.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlbs.Bridge.common.config.DefineConfig;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.domain.entity.UserData;
import com.qlbs.Bridge.module.youxifan.ios.param.AbstractParameter;
import com.qlbs.Bridge.repository.UserDataRepository;
import com.qlbs.Bridge.util.UrlUtil;

@Service
public class UserDataService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDataRepository UserDataRepository;

	/**
	 * gamekey_serverId_uid, GameKey和ServerId是固定的, uid可能重复所以账号可能重复. 这里不能改成
	 * 
	 * @param userId
	 * @param serverIdStr
	 * @param gameKey
	 * @param qdCode1
	 * @param qdCode2
	 * @return
	 * @return IResult
	 * @date 2018年12月3日上午11:06:18
	 */
	public IResult authenticate(String userId, String serverIdStr, String gameKey, String qdCode1) {
		String username = UrlUtil.utf8Decode(userId);
		AuthResult ar = null;
		try {
			int serverId = Integer.valueOf(serverIdStr);
			UserData userData = new UserData();
			userData.setUserId(userId);
			userData.setServerId(serverId);
			// 自定义名游戏名_服务器id_uid
			String gameName = DefineConfig.channelInfoMap.get(qdCode1);
			if (!StringUtils.isBlank(gameName)) {
				String identityId = gameName.concat("_").concat(serverIdStr).concat("_").concat(userData.getUserId());
				userData.setIdentityId(identityId);
				UserDataRepository.save(userData);
				ar = AuthResult.success(userData, username, gameKey);
			} else {
				ar = AuthResult.faild(ErrorCodeEnum.ERROR_CHANNEL);
			}
		} catch (Exception e) {
			logger.error("CommonService.save has an error", e);
			ar = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		}
		return ar;
	}

	/**
	 * gamekey_serverId_uid, GameKey和ServerId是固定的, uid可能重复所以账号可能重复. 这里不能改成
	 * 
	 * @param userId
	 * @param serverIdStr
	 * @param gameKey
	 * @param qdCode1
	 * @param qdCode2
	 * @return
	 * @return IResult
	 * @date 2018年12月3日上午11:06:18
	 */
	public IResult authenticate(AbstractParameter loginParam) {
		String username = UrlUtil.utf8Decode(loginParam.getUserId());
		AuthResult ar = null;
		try {
			int serverId = Integer.valueOf(loginParam.getServerId());
			UserData userData = new UserData();
			userData.setUserId(loginParam.getUserId());
			userData.setServerId(serverId);
			// 自定义名游戏名_服务器id_uid
			String gameName = DefineConfig.channelInfoMap.get(loginParam.getQdCode1());
			if (!StringUtils.isBlank(gameName)) {
				String identityId = gameName.concat("_").concat(loginParam.getServerId()).concat("_").concat(userData.getUserId());
				userData.setIdentityId(identityId);
				UserDataRepository.save(userData);
				ar = AuthResult.success(userData, username, loginParam.getGameKey());
			} else {
				ar = AuthResult.faild(ErrorCodeEnum.ERROR_CHANNEL);
			}
		} catch (Exception e) {
			logger.error("CommonService.save has an error", e);
			ar = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		}
		return ar;
	}

	/**
	 * gamekey_serverId_uid, GameKey和ServerId是固定的, uid可能重复所以账号可能重复. 这里不能改成
	 * 
	 * @param userId
	 * @param serverIdStr
	 * @param gameKey
	 * @param qdCode1
	 * @param qdCode2
	 * @return
	 * @return IResult
	 * @date 2018年12月3日上午11:06:18
	 */
	public IResult authenticate(Object object) {
		AbstractParameter loginParam = (AbstractParameter) object;
		String username = UrlUtil.utf8Decode(loginParam.getUserId());
		AuthResult ar = null;
		try {
			int serverId = Integer.valueOf(loginParam.getServerId());
			UserData userData = new UserData();
			userData.setUserId(loginParam.getUserId());
			userData.setServerId(serverId);
			// 自定义名游戏名_服务器id_uid
			String gameName = DefineConfig.channelInfoMap.get(loginParam.getQdCode1());
			if (!StringUtils.isBlank(gameName)) {
				String identityId = gameName.concat("_").concat(loginParam.getServerId()).concat("_").concat(userData.getUserId());
				userData.setIdentityId(identityId);
				UserDataRepository.save(userData);
				ar = AuthResult.success(userData, username, loginParam.getGameKey());
			} else {
				ar = AuthResult.faild(ErrorCodeEnum.ERROR_CHANNEL);
			}
		} catch (Exception e) {
			logger.error("CommonService.save has an error", e);
			ar = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		}
		return ar;
	}

}
