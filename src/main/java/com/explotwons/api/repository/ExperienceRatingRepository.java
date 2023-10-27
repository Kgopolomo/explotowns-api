package com.explotwons.api.repository;

import com.explotwons.api.entity.Experience;
import com.explotwons.api.entity.ExperienceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRatingRepository extends JpaRepository<ExperienceRating, Long> {

    Optional<ExperienceRating> findByExperienceAndUserId(Experience experience, Long userId);
}
