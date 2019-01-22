package com.qlbs.Bridge.service;

import java.util.*;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlbs.Bridge.domain.entity.PlayerInfo;
import com.qlbs.Bridge.repository.ExtractSessionFactory;
import com.qlbs.Bridge.repository.PlayerInfoRepository;

/**
 * 广州青蓝冰水有限公司
 * 
 * @auth Jimmy
 * @date:2018/11/21 22:02
 **/
@Service
public class PlayerInfoService {
	@Autowired
	private PlayerInfoRepository playerInfoRepository;

	@Autowired
	private ExtractSessionFactory sessionFactory;

	public void save(PlayerInfo info) {
		playerInfoRepository.save(info);
	}

	public Map<String, List<PlayerInfo>> getPlayerInfoList(String account) {
		Session session = sessionFactory.getSessionFactory().openSession();
		String sql = "select account,serverid,max(`offlinetime`) as maxofflinetime, max(`level`) as maxLevel from `player_info` where account='" + account + "' group by `serverid` order by `maxofflinetime` desc, `maxLevel` desc limit 20";
		List<Object[]> list = session.createSQLQuery(sql).list();
		if (list == null || list.size() <= 0)
			return new HashMap<>();

		Set<String> serverSet = new HashSet<>();

		StringBuffer sb = new StringBuffer();
		for (Object[] obj : list) {
			String serverid = obj[1].toString();
			if(serverSet.contains(serverid))
				continue;
			sb.append("'" + serverid + "',");
			serverSet.add(serverid);
		}
		String serverids = sb.substring(0, sb.length() - 1);
		sql = "select * from `player_info` where `account`= '"+account+"' and `serverid` in (" + serverids + ")";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(PlayerInfo.class));
		List<PlayerInfo> playinfoList = query.list();
		session.close();
		return list2Map(playinfoList);
	}

	private Map<String, List<PlayerInfo>> list2Map(List<PlayerInfo> playinfoList) {
		Map<String, List<PlayerInfo>> newMap = new HashMap<>();
		for (PlayerInfo info : playinfoList) {
			String serverid = info.getServerid();
			List<PlayerInfo> playerInfoList = newMap.get(serverid);
			if (playerInfoList == null) {
				playerInfoList = new ArrayList<>();
				newMap.put(serverid, playerInfoList);
			}
			playerInfoList.add(info);
		}
		return newMap;
	}
}
