package com.qlbs.Bridge.common.result.support;

import com.qlbs.Bridge.common.result.IResult;

/**
 * 订单校验返回结果集
 * 
 * @auth Jeremy
 * @date 2018年9月21日下午2:01:26
 */
public class OrderResult implements IResult {

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public IResult build() {
		return null;
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public String toStr() {
		return result;
	}

}
