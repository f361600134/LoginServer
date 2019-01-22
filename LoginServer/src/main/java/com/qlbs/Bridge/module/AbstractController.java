package com.qlbs.Bridge.module;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.SimpleResult;
import com.qlbs.Bridge.service.PayOrderService;
import com.qlbs.Bridge.service.UserDataService;
import com.qlbs.Bridge.util.CommonUtil;

public abstract class AbstractController implements IController {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserDataService userDataService;
	@Resource
	private PayOrderService payOrderService;

	public UserDataService getUserDataService() {
		return userDataService;
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

	public PayOrderService getPayOrderService() {
		return payOrderService;
	}

	public void setPayOrderService(PayOrderService payOrderService) {
		this.payOrderService = payOrderService;
	}

	@Override
	public IResult login(HttpServletRequest request, HttpServletResponse response) {
		info("This is AbstractController's login method");
		Map<String, String[]> paramMap = request.getParameterMap();
		try {
			Object param = CommonUtil.convertMap(getLoginParams(), paramMap);
			IResult result = checkParams(param);
			if (result == null) {
				// TODO
				info("验证成功...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			error("强转参数错误");
		}
		return null;
	}

	@Override
	public Class<?> getLoginParams() {
		return null;
	}

	/**
	 * 创建订单统一方法, 如果遇到某些平台方的参数不一样, 重写该方法即可
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @return IResult
	 * @date 2018年9月25日上午10:21:33
	 */
	@Override
	public IResult createOrder(HttpServletRequest request, HttpServletResponse response) {
		String qd1 = request.getParameter("qd1");
		String qd2 = request.getParameter("qd2");
		String playerId = request.getParameter("playerId");
		String playerName = request.getParameter("playerName");
		String gameKey = request.getParameter("gameKey");
		String serverId = request.getParameter("serverId");
		String eUrl = request.getParameter("eUrl");
		String price = request.getParameter("money");
		String gameMoney = request.getParameter("yuanbao");
		String sign = request.getParameter("sign");
		String userId = request.getParameter("userId");

		// 旧版本创建订单,不传userId则用playerId代替
		userId = userId == null ? playerId : userId;
		IResult result = checkParams(qd1, qd2, playerId, userId, playerName, gameKey, serverId, eUrl, price, gameMoney, sign);
		if (result != null) {
			error("Some of the parameters are null or empty");
			error("qd1:{}, qd2:{}, userId:{}, playerId:{}, playerName:{}, gameKey:{}, serverId:{}, eUrl:{}, price:{}, gameMoney:{}, gameMoney:{}, sign:{}", //
					qd1, qd2, userId, playerId, playerName, gameKey, serverId, eUrl, price, gameMoney, gameMoney, sign);
			return result;
		}
		result = getPayOrderService().createOrder(qd1, qd2, playerId, userId, playerName, gameKey, serverId, eUrl, price, gameMoney, sign);
		return result;
	}

	/**
	 * 判断参数对象
	 * 
	 * @param params
	 * @return
	 * @return IResult
	 * @date 2018年9月25日上午10:22:32
	 */
	public IResult checkParams(Object object) {
		Class<?> clazz = object.getClass();
		Set<Field> fieldSet = new HashSet<>();
		while (clazz != null) {
			fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
		}
		boolean bool = false;
		try {
			StringBuilder sb = new StringBuilder();
			for (Field field : fieldSet) {
				field.setAccessible(true);
				String name = field.getName();
				Object obj = field.get(object);
				if (obj == null) {
					info("校验失败, 参数为空, name:{}", name);
					bool = true;
				}
				sb.append(name).append("=").append(obj).append(", ");
			}
			info("参数信息, param:{}", sb.toString());
		} catch (Exception e) {
			error("校验参数异常, param:{}, e:{}", object, e);
		}
		return bool ? SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : null;
	}

	/**
	 * 判断参数
	 * 
	 * @param params
	 * @return
	 * @return IResult
	 * @date 2018年9月25日上午10:22:32
	 */
	public IResult checkParams(String... params) {
		boolean bool = StringUtils.isAnyBlank(params);
		return bool ? SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : null;
	}

	public void info(String infoMsg, Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(infoMsg, params);
		}
	}

	public void error(String infoMsg, Object... params) {
		if (logger.isErrorEnabled()) {
			logger.error(infoMsg, params);
		}
	}

	public void debug(String infoMsg, Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(infoMsg, params);
		}
	}

}
