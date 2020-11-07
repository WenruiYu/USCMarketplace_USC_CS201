package com.usc.market.repo;

import com.usc.market.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserEntityRepository
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 10:12 PM
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

    UserEntity findByUscId(String uscId);
}
