package com.qlbs.Bridge.interceptor;

import java.sql.Timestamp;
import java.util.Collection;

import com.qlbs.Bridge.util.DateUtils;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * 自定义分片算法
 * 
 * @author yinjihuan
 *
 */
public class ShardingAlgorithm implements PreciseShardingAlgorithm<Timestamp> {
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Timestamp> shardingValue) {
		Timestamp timestamp = shardingValue.getValue();
		String dayStr = String.valueOf(DateUtils.getDay(timestamp.getTime()));
		for (String tableName : availableTargetNames) {
			if (tableName.endsWith(dayStr)) {
				return tableName;
			}
		}
		throw new IllegalArgumentException();
	}
}
