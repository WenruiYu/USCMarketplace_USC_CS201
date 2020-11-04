package com.usc.market.repo;

import com.usc.market.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthorizationRepository
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 11:17 下午
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, String> {

    Authorization findByUsername(String username);

}
