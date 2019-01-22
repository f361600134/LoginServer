package com.qlbs.Bridge.module.quick;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qlbs.Bridge.common.ResultCode;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.AuthResult;
import com.qlbs.Bridge.util.HttpClientUtil;
import com.qlbs.Bridge.util.MD5;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Service
public class QuickService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 预验证
	 * 
	 * @param qdCode
	 * @param userId
	 * @param token
	 * @return
	 * @return IResult
	 * @date 2018年12月29日下午5:19:14
	 */
	public IResult preAuthenticate(Integer qdCode, String userId, String token) {
		IResult result = AuthResult.faild(ErrorCodeEnum.ERROR_UNKNOWN);
		StringBuilder sb = new StringBuilder(666).append(QuickConfig.requestUrl);
		sb.append("token=").append(token).append("&uid=").append(userId).append("&product_code=").append(QuickConfig.productCode);
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

	/**
	 * 校验签名 游戏收到3项数据后应校验签名是否正确,
	 * 计算md5(nt_data+sign+md5key)得到的本地密匙,与收到的md5Sign一致,则签名通过.
	 * 
	 * @param reChargeInfo
	 * @return
	 * @return true means
	 * @date 2018年9月10日下午6:26:51
	 */
	public boolean verifyOrder(String ntData, String sign, String md5Sign) {
		// ntData + sign + QuickConfig.md5Key;
		String signStr = new StringBuilder(ntData).append(sign).append(QuickConfig.md5Key).toString();
		String localMd5Sign = MD5.digest(signStr, true);
		if (!StringUtils.equals(localMd5Sign, md5Sign)) {
			logger.info("Quick 验证签名不相同,  localMd5Sign:{}, md5Sign:{}", localMd5Sign, md5Sign);
			return false;
		}
		return true;
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
		// 移除掉一个碍事儿的节点
		String key = QuickConfig.callbackKey;
		String realNtData = QuickSignUtil.decode(ntData, key);
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
		System.out.println(QuickConfig.test + ", " + StringUtils.equals(QuickConfig.test, "true"));
		if (!StringUtils.equals(QuickConfig.test, "true")) {
			if (StringUtils.equals(ResultCode.CODE_1, data.getIs_test())) {
				logger.info("测试模式已关闭, 测试订单不可用, 订单ID为: {}" + data.getGame_order());
				return null;
			}
		}
		return data;
	}

}
