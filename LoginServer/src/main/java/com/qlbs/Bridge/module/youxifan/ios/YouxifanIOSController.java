package com.qlbs.Bridge.module.youxifan.ios;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.support.SimpleResult;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.module.AbstractController;
import com.qlbs.Bridge.module.youxifan.android.YouxifanConfig;
import com.qlbs.Bridge.module.youxifan.ios.param.IExchargeParam;
import com.qlbs.Bridge.module.youxifan.ios.param.YouxifanIOSExchargeParam;
import com.qlbs.Bridge.module.youxifan.ios.param.YouxifanIOSLoginParam;
import com.qlbs.Bridge.util.CommonUtil;
import com.qlbs.Bridge.util.HttpClientUtil;

@RestController
@RequestMapping("/YouxifanIOS")
public class YouxifanIOSController extends AbstractController {

	@Override
	public Class<?> getLoginParams() {
		return YouxifanIOSLoginParam.class;
	}

	@Override
	public boolean checkLogin(Object object) {
		YouxifanIOSLoginParam param = (YouxifanIOSLoginParam) object;
		// 平台校验
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("token", param.getToken());
		String json = JSON.toJSON(paramsMap).toString();
		String result = null;
		try {
			result = HttpClientUtil.doHttpPost(YouxifanIOSConfig.requestUrl, json, ContentType.APPLICATION_JSON);
			if (!StringUtils.isBlank(result)) {
				Map<?, ?> map = JSON.parseObject(result, HashMap.class);
				Integer num = (Integer) map.get("code");
				// 如果不为0, 则验证失败
				int resultCode = Integer.compare(num.intValue(), 0);
				return resultCode == 0 ? true : false;
			}
		} catch (Exception e) {
			error("token验证发生异常, error:{}", e);
		}
		info("token验证失败, json:{}, token:{}, result:{}", json, param.getToken(), result);
		return false;
	}

	@Override
	public Class getExchargeParams() {
		return YouxifanIOSExchargeParam.class;
	}

	@Override
	public BusinessResult<?> sdkExcharge(IExchargeParam p, PayOrder payOrder) {
		YouxifanIOSExchargeParam param = (YouxifanIOSExchargeParam) p;

		BusinessResult<Boolean> result = new BusinessResult<>();
		Map<String, String> resultMap = CommonUtil.objToTreeMap(param, "sign");
		String localSign = CommonUtil.getMySignByMap(resultMap, YouxifanConfig.appkey);
		if (StringUtils.isAnyBlank(localSign, param.getSign())) {
			logger.error("签名为空,  localSign:{}, reChargeInfo:{}", localSign, param.getSign());
			result.setResult(SimpleResult.build(ErrorCodeEnum.IllEGAL_PARAMS));
			return result;
		}
		if (StringUtils.equals(param.getSign(), localSign)) {
			result.setResult(SimpleResult.build(ErrorCodeEnum.SUCCESS));
			return result;
		}
		return result;
	}

}
