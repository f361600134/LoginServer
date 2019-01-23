package com.qlbs.Bridge.module.common.result;

import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.ExchargeResult;

public class BusinessResult<T> {

	private IResult result;// 用于系统的错误集
	private T object;// 用于sdk平台的错误集

	public IResult getResult() {
		return result;
	}

	public void setResult(IResult result) {
		this.result = result;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "BusinessResult [result=" + result + ", object=" + object + "]";
	}

	public BusinessResult(IResult result) {
		this.result = result;
	}

	public BusinessResult() {
		this.result = ExchargeResult.build(ErrorCodeEnum.ERROR_UNKNOWN);
	}

	public BusinessResult(T t) {
		this.result = ExchargeResult.build(ErrorCodeEnum.ERROR_UNKNOWN);
		this.object = t;
	}

}
