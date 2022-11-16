package com.maketogether.membership.user.repository;

import com.maketogether.membership.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
