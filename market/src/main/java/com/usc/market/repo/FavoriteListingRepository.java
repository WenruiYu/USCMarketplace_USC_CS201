package com.usc.market.repo;

import com.usc.market.model.FavoriteListingEntity;
import com.usc.market.model.FavoriteListingEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FavoriteListingRepository extends JpaRepository<FavoriteListingEntity, FavoriteListingEntityPK> {

    List<FavoriteListingEntity> findAllByUserIdOrderByGmtCreateDesc(int userId);

}
