package com.explotwons.api.repository;

import com.explotwons.api.entity.Township;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownshipRepository extends JpaRepository<Township, Long> {
    List<Township> findByLocationProvince(String province);
}
