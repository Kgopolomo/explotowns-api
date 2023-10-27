package com.explotwons.api.repository;

import com.explotwons.api.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long > ,JpaSpecificationExecutor<Experience> {
    List<Experience> findByCategory(String category);
    List<Experience> findByTownship(Long townshipId);

}
