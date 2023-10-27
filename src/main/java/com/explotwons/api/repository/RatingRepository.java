package com.explotwons.api.repository;

import com.explotwons.api.entity.Rating;
import com.explotwons.api.entity.Township;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating>  findByTownshipAndUserId(Township township, Long userId);
}
