package com.explotwons.api.repository;

import com.explotwons.api.entity.SavedExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedExperienceRepository extends JpaRepository<SavedExperience, Long> {

    List<SavedExperience> findByUserId(Long userId);
    Optional<SavedExperience> findByUserIdAndExperienceId(Long userId, Long experienceId);
}
