package com.usc.market.repo;

import com.usc.market.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthorizationRepository
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 11:17 PM
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<AuthEntity, String> {

    AuthEntity findByUsername(String username);

}
