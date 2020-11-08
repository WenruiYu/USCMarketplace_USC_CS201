package com.usc.market.repo;

import com.usc.market.model.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<ListingEntity, Integer> {
    List<ListingEntity> findAllByOrderByIdDesc();
}
