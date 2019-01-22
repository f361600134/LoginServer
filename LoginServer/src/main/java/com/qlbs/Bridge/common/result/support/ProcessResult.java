package com.qlbs.Bridge.common.result.support;

import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;

/**
 * 进度返回结果集
 * 
 * @auth Jeremy
 * @date 2018年9月21日下午2:01:38
 */
public final class ProcessResult extends AbstractResult {

	private final String orderId;

	public String getOrderId() {
		return orderId;
	}

	public ProcessResult(ErrorCodeEnum codeEnum, String orderId) {
		// super(processEnum.getStatus(), processEnum.getDesc());
		super(codeEnum);
		this.orderId = orderId;
	}

	public static ProcessResult build(ErrorCodeEnum processEnum, String orderId) {
		ProcessResult result = new ProcessResult(processEnum, orderId);
		return result;
	}

	@Override
	public String toStr() {
		// TODO Auto-generated method stub
		return null;
	}
}
