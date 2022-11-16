package com.maketogether.membership.userinfo.repository;

import com.maketogether.membership.userinfo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
