package com.qlbs.Bridge.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qlbs.Bridge.common.config.DefineConfig;
import com.qlbs.Bridge.common.result.ErrorCodeEnum;
import com.qlbs.Bridge.common.result.IResult;
import com.qlbs.Bridge.common.result.support.ProcessResult;
import com.qlbs.Bridge.domain.ExchargeResult;
import com.qlbs.Bridge.domain.OrderStatusEnum;
import com.qlbs.Bridge.domain.entity.PayOrder;
import com.qlbs.Bridge.repository.PayOrderRepostory;
import com.qlbs.Bridge.util.HttpClientUtil;
import com.qlbs.Bridge.util.MD5;
import com.qlbs.Bridge.util.UrlUtil;

@Service
public class PayOrderService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PayOrderRepostory payOrderRepostory;

	/**
	 * 创建订单
	 * 
	 * @param qd1
	 * @param qd2
	 * @param playerId
	 * @param playerName
	 * @param gameKey
	 * @param serverId
	 * @param eUrl
	 * @param price
	 * @param gameMoney
	 * @param sign
	 * @return
	 * @return IResult
	 * @date 2018年10月8日上午9:54:11
	 */
	public IResult createOrder(String qd1, String qd2, String userId, String playerId, String playerName, String gameKey, String serverId, String eUrl, String price, String gameMoney, String sign) {
		IResult result = null;
		String sysOrderId = null;
		try {
			eUrl = UrlUtil.decode(eUrl);
			eUrl = eUrl + "/services";
			BigDecimal amount = new BigDecimal(price);
			amount = amount.setScale(2, 6);
			playerName = UrlUtil.decode(playerName);

			sysOrderId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
			PayOrder order = new PayOrder();
			order.setOrderId(sysOrderId);
			order.setServerId(Integer.valueOf(serverId).intValue());
			order.setExchangeUrl(eUrl);
			order.setPlayerId(playerId);
			order.setPlayerName(playerName);
			order.setUserId(userId);
			order.setIdentityName(playerId);
			order.setMoneyBig(amount);
			order.setQdCode1(Integer.valueOf(qd1).intValue());
			order.setQdCode2(Integer.valueOf(qd2).intValue());
			order.setGameKey(gameKey);
			order.setMoney(Float.valueOf(price).floatValue());
			order.setGameMoney(Integer.valueOf(gameMoney).intValue());
			order.setPayStatus(OrderStatusEnum.Order_Created_Paying);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			order.setCreationTime(createTime);
			order.setUpdateTime(createTime);

			payOrderRepostory.save(order);
			result = ProcessResult.build(ErrorCodeEnum.SUCCESS, order.getOrderId());
			logger.info("PayOrderService.create successful, orderId:{}", sysOrderId);
		} catch (Exception e) {
			result = ProcessResult.build(ErrorCodeEnum.ERROR_ORDER_CREATE, sysOrderId);
			logger.error("", e);
			logger.error("PayOrderService.create has an error, orderId:{}", sysOrderId);
		}
		return result;
	}

	/**
	 * 获取到一个订单
	 * 
	 * @param orderId
	 * @return
	 * @return PayOrder
	 * @date 2018年9月25日上午11:01:02
	 */
	public PayOrder getPayOrder(String orderId) {
		try {
			PayOrder payOrder = payOrderRepostory.findOne(orderId);
			if (payOrder == null) {
				logger.error("订单不存在：" + orderId);
				return null;
			}
			if (payOrder.getStatus() == OrderStatusEnum.Order_Exchanged_Success.getStatus()) {
				logger.error("该订单已经处理过了！payOrder:{}" + payOrder);
				return null;
			}
			return payOrder;
		} catch (Exception e) {
			logger.error("获取订单时发生异常, 异常信息:", e);
			return null;
		}
	}

	/**
	 * 充值前的订单校验
	 * 
	 * @param orderId
	 * @param purchaseState
	 * @param purchaseToken
	 * @return true: 校验成功, false:校验失败
	 */
	public PayOrder checkExcharge(String orderId, String channelOrderId) {
		try {
			// String orderId = rechargeInfo.getCpOrderId();
			// String channelOrderId = rechargeInfo.getOrderId();
			PayOrder payOrder = payOrderRepostory.findOne(orderId);

			if (payOrder == null) {
				logger.error("充值失败，订单不存在：" + orderId);
				return null;
			}
			if (payOrder.getStatus() == OrderStatusEnum.Order_Exchanged_Success.getStatus()) {
				logger.error("该订单已经处理过了！payOrder:{}" + payOrder);
				return null;
			}
			payOrder.setPayStatus(OrderStatusEnum.Order_Confirmed_Exchanging);
			payOrder.setChannelOrderId(channelOrderId);
			// 兑换游戏币
			return payOrder;
		} catch (Exception e) {
			logger.error("处理订单过程出错，请检查相关信息！", e);
			return null;
		}
	}

	/**
	 * 兑换游戏货币
	 * 
	 * @param payOrder
	 * @return
	 */
	@Async("taskAsyncPool")
	public void exchangeGameMoney(PayOrder payOrder) {
		exchangeGameMoney(payOrder, "", "", false);
	}

	/**
	 * 兑换游戏货币
	 * 
	 * @param payOrder
	 * @return
	 */
	@Async("taskAsyncPool")
	public void exchangeGameMoney(PayOrder payOrder, String productId, String moneyType, boolean useServerGameMoney) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String gameKey = DefineConfig.gameKeyMap.get(payOrder.getGameKey());
		String gameGold = String.valueOf(payOrder.getGameMoney());
		String userId = payOrder.getUserId();
		String playerName = payOrder.getPlayerName();
		String moneyBig = String.valueOf(payOrder.getMoneyBig());
		String sysOrderId = payOrder.getOrderId();

		StringBuilder builder = new StringBuilder();
		builder.append(userId).append(playerName).append(gameGold).append(moneyBig).append(gameKey);
		// String md5Str = userId + playerName + gameGold + moneyBig + gameKey;
		String md5Str = builder.toString();
		String sign = MD5.digest(builder.toString());

		params.add(new BasicNameValuePair("identityName", userId));
		params.add(new BasicNameValuePair("playerName", UrlUtil.encode(playerName)));
		params.add(new BasicNameValuePair("gameGold", gameGold));
		params.add(new BasicNameValuePair("payMoney", moneyBig));
		params.add(new BasicNameValuePair("payTime", String.valueOf(payOrder.getCreationTime().getTime())));
		params.add(new BasicNameValuePair("remarks", payOrder.getRemark()));
		params.add(new BasicNameValuePair("refId", productId));
		params.add(new BasicNameValuePair("moneyType", moneyType));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("sysOrderId", sysOrderId));
		params.add(new BasicNameValuePair("action", "pay"));

		String tarUrl = payOrder.getExchangeUrl();
		logger.info("exchangeGameMoney, Finished to pack parameters, md5Str:{}, sign:{}, tarUrl:{}", md5Str, sign, tarUrl);
		try {
			String result = HttpClientUtil.doGet(tarUrl, params);
			if (StringUtils.isBlank(result)) {
				// 充值成功, 解析json,判断code值,修改状态
				ExchargeResult exchargeResult = JSONObject.parseObject(result, ExchargeResult.class);

				// 如果为港澳台bridge，则使用服务端返回的元宝数
				if (useServerGameMoney) {
					payOrder.setGameMoney(exchargeResult.getGameMoney());
				}

				if (exchargeResult.isSuccess()) {
					// 发放游戏币成功，修改GM后台订单状态
					payOrder.setPayStatus(OrderStatusEnum.Order_Exchanged_Success);
					payOrder.appendTrace(payOrder.getStatusDesc());
					payOrderRepostory.save(payOrder);

					if (logger.isInfoEnabled())
						logger.info("订单完成确认,发放游戏币成功,订单信息: " + payOrder.toString());
				} else {
					// 发放游戏币失败, 修改订单状态
					payOrder.setPayStatus(OrderStatusEnum.Order_Exchanged_Failure);
					payOrder.appendTrace(exchargeResult.getDescription());
					payOrderRepostory.save(payOrder);

					if (logger.isInfoEnabled()) {
						logger.info("服务端发放游戏币失败，返回来的信息是： ,订单信息: " + payOrder.toString() + result);
					}
				}
			} else {
				// http请求异常, 充值失败失败
				payOrder.setPayStatus(OrderStatusEnum.Order_Exchanged_Break);
				payOrder.appendTrace(payOrder.getStatusDesc());
				payOrderRepostory.save(payOrder);
				logger.error("订单完成确认,发放游戏币失败,订单信息:{}, md5Str:{}, sign:{}, tarUrl:{} ", payOrder.toString(), md5Str, sign, tarUrl);
			}
		} catch (Exception e) {
			logger.error("The parameters are identityName:{}, playerName:{}, gameGold:{}, remarks:{}, moneyType:{}, payMoneybig:{}, tarUrl:{}, sign:{}", userId, playerName, gameGold, payOrder.getRemark(), moneyType, moneyBig, tarUrl, sign);
			logger.error("订单完成确认,发放游戏币时发生异常!", e);
		}
	}

}
