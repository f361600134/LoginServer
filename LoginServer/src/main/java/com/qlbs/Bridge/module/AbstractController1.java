package com.qlbs.Bridge.module;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qlbs.Bridge.common.annotation.Filed;
import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.ExchargeResult;
import com.qlbs.Bridge.service.PayOrderService;
import com.qlbs.Bridge.service.UserDataService;

public abstract class AbstractController1 implements IController {

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
			clazz = clazz.getSuperclass();
		}
		boolean bool = false;
		try {
			StringBuilder sb = new StringBuilder();
			for (Field field : fieldSet) {
				field.setAccessible(true);
				String name = field.getName();
				Object obj = field.get(object);
				Filed filed = field.getAnnotation(Filed.class);
				if (filed == null && obj == null) {
					info("1校验失败, 参数为空, name:{}", name);
					bool = true;
				} else if (filed != null && !filed.isNull() && obj == null) {
					info("2校验失败, 参数为空, name:{}", name);
					bool = true;
				}
				sb.append(name).append("=").append(obj).append(", ");
			}
			sb.deleteCharAt(sb.length() - 1);
			info("参数信息, param:{}", sb.toString());
		} catch (Exception e) {
			error("校验参数异常, param:{}, e:{}", object, e);
		}
		return bool ? AbstractResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : AbstractResult.build(ErrorCodeEnum.SUCCESS);
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
		return bool ? ExchargeResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : null;
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
