package com.usc.market.repo;

import com.usc.market.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthEntity, String> {

    AuthEntity findByUsername(String username);

}
