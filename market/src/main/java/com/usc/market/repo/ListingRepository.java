package com.usc.market.repo;

import com.usc.market.model.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<ListingEntity, Integer> {
}
