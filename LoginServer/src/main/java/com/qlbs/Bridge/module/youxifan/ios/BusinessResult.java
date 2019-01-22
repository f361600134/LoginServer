package com.qlbs.Bridge.module.youxifan.ios;

import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.SimpleResult;
import com.qlbs.Bridge.module.youxifan.ios.param.YouxifanIOSLoginParam;

public class BusinessResult<T> {

	private IResult result;
	private T object;

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

	public static void main(String[] args) {
		BusinessResult<Boolean> result = new BusinessResult<>();
		result.setResult(SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS));
		result.setObject(false);
		System.out.println(result);

		BusinessResult<YouxifanIOSLoginParam> result1 = new BusinessResult<>();
		result1.setResult(SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS));
		YouxifanIOSLoginParam object = new YouxifanIOSLoginParam("111");
		result1.setObject(object);
		System.out.println(result1);

	}

	public BusinessResult(IResult result) {
		this.result = result;
	}

	public BusinessResult() {
		this.result = SimpleResult.build(ErrorCodeEnum.ERROR_UNKNOWN);
	}

}
