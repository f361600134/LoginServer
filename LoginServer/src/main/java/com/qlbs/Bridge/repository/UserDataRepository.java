package com.qlbs.Bridge.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.qlbs.Bridge.domain.entity.UserData;

@Repository
@Qualifier(value = "userDataRepository")
public interface UserDataRepository extends CrudRepository<UserData, String> {

}
