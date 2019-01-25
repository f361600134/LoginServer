package com.qlbs.Bridge.module;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.annotation.ParameterMapping;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.common.result.support.OrderResult;
import com.qlbs.Bridge.domain.OrderStatusEnum;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.common.impl.IExchargeParam;
import com.qlbs.Bridge.module.common.impl.PreOrderParam;
import com.qlbs.Bridge.util.CommonUtil;

public abstract class AbstractParamController extends AbstractController1 {

	/**
	 * sdk登录验证
	 * 
	 * @param param
	 * @return
	 * @return boolean
	 * @date 2019年1月23日下午7:26:26
	 */
	public abstract IResult sdkLogin(Object object);

	/**
	 * sdk创建订单验证
	 * 
	 * @param param
	 * @return
	 * @return boolean
	 * @date 2019年1月23日下午7:26:26
	 */
	public IResult sdkOrder(PayOrder order) {
		return OrderResult.build(ErrorCodeEnum.SUCCESS, order.getOrderId());
	}

	/**
	 * sdk充值验证
	 * 
	 * @return
	 * @return BusinessResult
	 * @date 2019年1月22日下午7:35:37
	 */
	public abstract IResult sdkExcharge(IExchargeParam object);

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
			if (checkParams(param).isSuccess() && sdkLogin(param).isSuccess()) {
				return getUserDataService().authenticate(param);
			}
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
			if (checkParams(param).isSuccess()) {
				PayOrder payOrder = getPayOrderService().createOrder1(param);
				if (payOrder != null)
					result = sdkOrder(payOrder);
				else {
					result = OrderResult.build(ErrorCodeEnum.ERROR_ORDER_CREATE);
				}
			} else {
				result = OrderResult.build(ErrorCodeEnum.IllEGAL_PARAMS);
			}
		} catch (Exception e) {
			error("创建订单出错:{}", e);
		}
		return result;
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
			IResult result = null;
			// 验证订单信息
			if ((result = checkParams(param)).isSuccess() && (result = sdkExcharge(param)).isSuccess()) {
				// 获取到有效订单信息
				PayOrder payOrder = getPayOrderService().getPayOrder(param.getOrderId());
				// 兑换游戏币
				getPayOrderService().exchangeGameMoney(payOrder);
				payOrder.setPayStatus(OrderStatusEnum.Order_Confirmed_Exchanging);
				payOrder.setChannelOrderId(param.getOrderId());
			} else {
				info("充值回调数据校验错误, IExchargeParam:{}", param);
			}
			resultCode = result.getResult();
		} catch (Exception e) {
			resultCode = ResultCode.CODE_RINNING_ERROR;
			info("充值回调出现异常, IExchargeParam:{}", param);
			error("充值回调出现异常, e:{}", e);
		}
		return resultCode;
	}

}
