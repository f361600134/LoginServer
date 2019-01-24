package com.qlbs.Bridge.module.youxifan.android2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.annotation.ParameterMapping;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.ExchargeResult;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.AbstractController;
import com.qlbs.Bridge.module.common.impl.IExchargeParam;
import com.qlbs.Bridge.module.youxifan.android2.param.YouxifanExchargeParam;
import com.qlbs.Bridge.module.youxifan.android2.param.YouxifanLoginParam;
import com.qlbs.Bridge.util.CommonUtil;
import com.qlbs.Bridge.util.HttpClientUtil;

@RestController
@RequestMapping("/youxifan2")
@ParameterMapping(loginParam = YouxifanLoginParam.class, exchargeParam = YouxifanExchargeParam.class)
public class Youxifan2Controller extends AbstractController {

	@Override
	public boolean sdkLogin(Object param) {
		YouxifanLoginParam loginParam = (YouxifanLoginParam) param;
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("sessionId", loginParam.getSessionId()));
		list.add(new BasicNameValuePair("platformId", loginParam.getPlatformId()));
		list.add(new BasicNameValuePair("appId", String.valueOf(Youxifan2Config.YG_APPI)));
		try {
			String resultJson = HttpClientUtil.doGet(Youxifan2Config.requestUrl, list);
			if (StringUtils.isBlank(resultJson)) {
				return false;
			}
			Map<?, ?> map = JSON.parseObject(resultJson, HashMap.class);
			String code = (String) map.get("code");
			if (StringUtils.equals(code, "0")) {
				return true;
			}
		} catch (Exception e) {
			error("youxifan2 sdk校验异常:{}", e);
		}
		return false;
	}

	@Override
	public IResult sdkOrder(PayOrder order) {
		Map<String, String> map = new HashMap<>();
		map.put("appId", String.valueOf(Youxifan2Config.YG_APPI));
		map.put("uid", order.getUserId());
		map.put("cpOrderId", order.getOrderId());
		map.put("roleId", order.getPlayerId());
		map.put("zoneId", "1");
		map.put("amount", String.valueOf(order.getMoneyBig().intValue() * 100));
		String sign = CommonUtil.getMySignByMap(map, Youxifan2Config.appkey);
		YouxifanOrderResult result = YouxifanOrderResult.build(ErrorCodeEnum.SUCCESS, order.getOrderId(), sign);
		return result;
	}

	/**
	 * @param
	 * @return 0: 支付成功, 1: 签名失败, 2:未知错误
	 */
	@Override
	public IResult sdkExcharge(IExchargeParam param, PayOrder payOrder) {
		IResult result = null;
		YouxifanExchargeParam exchargeParam = (YouxifanExchargeParam) param;
		Map<String, String> map = CommonUtil.objToTreeMap(exchargeParam);
		String localSign = CommonUtil.getMySignByMap(map, Youxifan2Config.appkey);
		// 验签
		if (StringUtils.equals(localSign, exchargeParam.getSign())) {
			result = ExchargeResult.build(ErrorCodeEnum.SUCCESS, ResultCode.CODE_0);
		} else {
			result = ExchargeResult.build(ErrorCodeEnum.IllEGAL_PARAMS, ResultCode.CODE_1);
			info("验签失败, exchargeParam:{}, localSign:{}", exchargeParam, localSign);
		}
		return result;
	}

}
