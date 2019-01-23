package com.qlbs.Bridge.common.result.support;

import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;

/**
 * 支付的的结果集对象, 用于支付的结果返回集.
 * 
 * @auth Jeremy
 * @date 2019年1月23日下午5:16:58
 */
public class ExchargeResult extends AbstractResult {

	private String result;

	@Override
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ExchargeResult(ErrorCodeEnum codeEnum) {
		super(codeEnum);
	}

	public ExchargeResult(ErrorCodeEnum codeEnum, String result) {
		super(codeEnum);
		this.result = result;
	}

	public static IResult build(ErrorCodeEnum codeEnum) {
		return new ExchargeResult(codeEnum);
	}

	public static IResult build(ErrorCodeEnum codeEnum, String result) {
		return new ExchargeResult(codeEnum, result);
	}

}
