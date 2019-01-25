package com.qlbs.Bridge.module.quick.all1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.annotation.ParameterMapping;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.module.AbstractParamController;
import com.qlbs.Bridge.module.common.impl.IExchargeParam;
import com.qlbs.Bridge.module.quick.all.QuickConfig;
import com.qlbs.Bridge.module.quick.all.QuickSignUtil;
import com.qlbs.Bridge.module.quick.all1.param.QuickData;
import com.qlbs.Bridge.module.quick.all1.param.QuickExchargeParam;
import com.qlbs.Bridge.module.quick.all1.param.QuickLoginParam;
import com.qlbs.Bridge.util.HttpClientUtil;
import com.qlbs.Bridge.util.MD5;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RestController
@RequestMapping("/quick2")
@ParameterMapping(exchargeParam = QuickExchargeParam.class, loginParam = QuickLoginParam.class)
public class Quick2Controller extends AbstractParamController {

	@Override
	public IResult sdkLogin(Object object) {
		QuickLoginParam loginParam = (QuickLoginParam) object;
		IResult result = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		StringBuilder sb = new StringBuilder(666).append(QuickConfig.requestUrl);
		sb.append("token=").append(loginParam.getToken()).append("&uid=").append(loginParam.getUserId()).append("&product_code=").append(QuickConfig.productCode);
		try {
			String status = HttpClientUtil.doGet(sb.toString());
			// 验证失败,返回前端
			if (!StringUtils.equals(status, "1")) {
				return result;
			}
		} catch (Exception e) {
			logger.error("Quick 预验证出错, e:" + e);
		}
		return result;
	}

	@Override
	public IResult sdkExcharge(IExchargeParam object) {
		IResult result = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		QuickExchargeParam exchargeParam = (QuickExchargeParam) object;
		// ntData + sign + QuickConfig.md5Key;
		String signStr = new StringBuilder(exchargeParam.getNt_data()).append(exchargeParam.getSign()).append(QuickConfig.md5Key).toString();
		String localMd5Sign = MD5.digest(signStr, true);
		if (!StringUtils.equals(localMd5Sign, exchargeParam.getMd5Sign())) {
			logger.info("Quick 验证签名不相同,  localMd5Sign:{}, md5Sign:{}", localMd5Sign, exchargeParam.getMd5Sign());
			result = AuthResult.faild(ErrorCodeEnum.ERROR_SIGN);
			return result;
		}
		// transfer data
		QuickData data = strToQuickData(exchargeParam.getNt_data());
		object.setIExchargeParam(data);
		result = AuthResult.faild(ErrorCodeEnum.SUCCESS);
		return result;
	}

	/**
	 * 
	 * @param ntData
	 * @param sign
	 * @param md5Sign
	 * @return
	 * @return boolean
	 * @date 2019年1月3日下午2:28:41
	 */
	public QuickData strToQuickData(String ntData) {
		if (StringUtils.isBlank(ntData)) {
			logger.info("Quick充值失败, 参数ntData为空,  ntData:{}", ntData);
			return null;
		}

		String key = QuickConfig.callbackKey;
		String realNtData = QuickSignUtil.decode(ntData, key);
		// 移除掉一个碍事儿的节点
		realNtData = realNtData.replaceAll("\\<quicksdk_message>|\\</quicksdk_message>", "");
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("message", QuickData.class);
		// 转换bean
		QuickData data = (QuickData) xStream.fromXML(realNtData);
		if (data == null) {
			logger.info("Quick充值失败, 生成QuickData为null. realNtData:{}, key:{}", realNtData, key);
			return null;
		}
		// 0成功, 1失败
		if (!StringUtils.equals(data.getStatus(), ResultCode.CODE_0)) {
			logger.info("Quick充值失败, 渠道返回状态为失败状态(0), data:" + data);
			return null;
		}
		// 正式服务器, 如果测试账号, 则视为失败
		logger.info(QuickConfig.test + ", " + StringUtils.equals(QuickConfig.test, "true"));
		if (!StringUtils.equals(QuickConfig.test, "true")) {
			if (StringUtils.equals(ResultCode.CODE_1, data.getIs_test())) {
				logger.info("测试模式已关闭, 测试订单不可用, 订单ID为: {}" + data.getGame_order());
				return null;
			}
		}
		return data;
	}
}
