package com.qlbs.Bridge.common.result.support;

import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;

/**
 * 用于用户验证创建订单, 订单校验返回结果集
 * 
 * @auth Jeremy
 * @date 2018年9月21日下午2:01:26
 */
public class OrderResult extends AbstractResult {

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderResult(ErrorCodeEnum codeEnum) {
		super(codeEnum);
	}

	public OrderResult(ErrorCodeEnum codeEnum, String orderId) {
		super(codeEnum);
		this.orderId = orderId;
	}

	public static OrderResult build(ErrorCodeEnum processEnum, String orderId) {
		OrderResult result = new OrderResult(processEnum, orderId);
		return result;
	}

	public static OrderResult build(ErrorCodeEnum processEnum) {
		OrderResult result = new OrderResult(processEnum);
		return result;
	}

}
