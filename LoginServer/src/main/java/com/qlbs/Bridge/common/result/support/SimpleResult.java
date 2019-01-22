package com.qlbs.Bridge.common.result.support;

import com.qlbs.Bridge.common.result.AbstractResult;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;

public class SimpleResult extends AbstractResult {

	public SimpleResult(ErrorCodeEnum codeEnum) {
		super(codeEnum);
	}

	public static IResult build(ErrorCodeEnum codeEnum) {
		return new SimpleResult(codeEnum);
	}

}
