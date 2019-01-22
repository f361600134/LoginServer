package com.qlbs.Bridge.repository;

import com.qlbs.Bridge.domain.entity.PlayerInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 广州青蓝冰水有限公司
 * @auth Jimmy
 * @date:2018/11/21 21:59
 **/
@Repository
@Qualifier(value = "playerInfoRepository")
public interface PlayerInfoRepository extends CrudRepository<PlayerInfo, String> {
}
