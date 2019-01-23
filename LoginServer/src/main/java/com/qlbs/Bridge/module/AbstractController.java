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

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.annotation.ParameterMapping;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.common.result.support.ExchargeResult;
import com.qlbs.Bridge.common.result.support.OrderResult;
import com.qlbs.Bridge.domain.OrderStatusEnum;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.common.impl.IExchargeParam;
import com.qlbs.Bridge.module.common.impl.PreOrderParam;
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
			ParameterMapping parameterMapping = this.getClass().getAnnotation(ParameterMapping.class);
			if (parameterMapping == null || parameterMapping.loginParam() == null) {
				info("登录参数配置为空, 请检查是否配置[@ParameterMapping]注解");
				return AuthResult.faild(ErrorCodeEnum.ERROR_RUNNING);
			}
			Object param = CommonUtil.convertMap(parameterMapping.loginParam(), paramMap);
			IResult result = checkParams(param);
			if (result != null) {
				return AuthResult.faild(ErrorCodeEnum.IllEGAL_PARAMS);
			}
			boolean bool = sdkLogin(param);
			if (!bool) {
				info("sdk校验出错", param);
				return AuthResult.faild(ErrorCodeEnum.ERROR_SIGN);
			}
			return getUserDataService().authenticate(param);
		} catch (Exception e) {
			error("登录出错, e:{}", e);
		}
		return AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
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
		Map<String, String[]> paramMap = request.getParameterMap();
		ParameterMapping parameterMapping = this.getClass().getAnnotation(ParameterMapping.class);
		if (parameterMapping == null) {
			info("登录参数配置为空, 请检查是否配置[@ParameterMapping]注解");
			return OrderResult.build(ErrorCodeEnum.ERROR_RUNNING);
		}
		IResult result = null;
		PreOrderParam param = null;
		try {
			param = (PreOrderParam) CommonUtil.convertMap(parameterMapping.orderParam(), paramMap);
			result = checkParams(param);
			if (result != null) {
				return OrderResult.build(ErrorCodeEnum.IllEGAL_PARAMS);
			}
			result = getPayOrderService().createOrder(param);
			if (result.isSuccess())
				result = sdkOrder(param);
		} catch (Exception e) {
			error("创建订单出错:{}", e);
		}
		return result;
	}

	/**
	 * sdk登录验证
	 * 
	 * @param param
	 * @return
	 * @return boolean
	 * @date 2019年1月23日下午7:26:26
	 */
	public boolean sdkLogin(Object object) {
		return false;
	}

	/**
	 * sdk创建订单验证
	 * 
	 * @param param
	 * @return
	 * @return boolean
	 * @date 2019年1月23日下午7:26:26
	 */
	public IResult sdkOrder(PreOrderParam param) {
		return OrderResult.build(ErrorCodeEnum.SUCCESS);
	}

	/**
	 * sdk充值验证
	 * 
	 * @return
	 * @return BusinessResult
	 * @date 2019年1月22日下午7:35:37
	 */
	public IResult sdkExcharge(IExchargeParam object, PayOrder payOrder) {
		return null;
	}

	/**
	 *
	 * @return 0：接收成功 1:签名错误 2：未知错误
	 */
	@Override
	public String excharge(HttpServletRequest request, HttpServletResponse response) {
		String resultCode = null;
		info("This is AbstractController's excharge method");
		Map<String, String[]> paramMap = request.getParameterMap();
		IExchargeParam param = null;
		try {
			ParameterMapping parameterMapping = this.getClass().getAnnotation(ParameterMapping.class);
			if (parameterMapping == null || parameterMapping.loginParam() == null) {
				info("充值参数配置为空, 请检查是否配置@ParameterMapping参数");
				return ResultCode.CODE_RINNING_ERROR;// 返回系统内部的错误信息
			}
			param = (IExchargeParam) CommonUtil.convertMap(parameterMapping.exchargeParam(), paramMap);
			IResult result = checkParams(param);
			if (result != null) {
				return ResultCode.CODE_RINNING_ERROR;// 返回系统内部的错误信息
			}
			// 获取到有效订单信息
			PayOrder payOrder = getPayOrderService().getPayOrder(param.getOrderId());
			if (payOrder != null) {
				// 验证订单信息
				IResult businessResult = sdkExcharge(param, payOrder);
				if (businessResult.isSuccess()) {
					// 兑换游戏币
					getPayOrderService().exchangeGameMoney(payOrder);
				} else {
					info("充值回调数据校验错误, IExchargeParam:{}", param);
				}
				resultCode = businessResult.getResult();
				payOrder.setPayStatus(OrderStatusEnum.Order_Confirmed_Exchanging);
				payOrder.setChannelOrderId(param.getOrderId());
			} else {
				resultCode = ResultCode.CODE_ORDER_ERROR;
				info("充值失败订单有误, IExchargeParam:{}", param);
			}
		} catch (Exception e) {
			resultCode = ResultCode.CODE_RINNING_ERROR;
			info("充值回调出现异常, IExchargeParam:{}", param);
			error("充值回调出现异常, e:{}", e);
		}
		return resultCode;
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
				if (obj == null) {
					info("校验失败, 参数为空, name:{}", name);
					bool = true;
				}
				sb.append(name).append("=").append(obj).append(", ");
			}
			sb.deleteCharAt(sb.length() - 1);
			info("参数信息, param:{}", sb.toString());
		} catch (Exception e) {
			error("校验参数异常, param:{}, e:{}", object, e);
		}
		return bool ? ExchargeResult.build(ErrorCodeEnum.IllEGAL_PARAMS) : null;
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
