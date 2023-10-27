package com.explotwons.api.controller;

import com.explotwons.api.entity.Experience;
import com.explotwons.api.entity.ExperienceRating;
import com.explotwons.api.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public List<Experience> getAllExperiences(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double averageRating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order
    ) {
        return experienceService.getAllExperiences(category, province, date, minPrice, maxPrice, averageRating, sortBy,order);
    }

    @PostMapping
    public Experience addExperience(@RequestParam(required = true) Long townshipId, @RequestBody Experience experience) {
        return experienceService.addExperience(townshipId, experience);
    }

    @PutMapping("/{experienceId}")
    public Experience updateExperience(@PathVariable Long experienceId, @RequestBody Experience experience) {
        return experienceService.updateExperience(experienceId, experience);
    }

    @GetMapping("/{experienceId}")
    public Experience getExperienceById(@PathVariable Long experienceId) {
        return experienceService.getExperienceById(experienceId);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Experience>> getTopRatedExperiences() {
        List<Experience> topRatedExperiences = experienceService.getTopRatedExperiences();
        return ResponseEntity.ok(topRatedExperiences);
    }

    @DeleteMapping("/{experienceId}")
    public void deleteExperience(@PathVariable Long experienceId) {
        experienceService.deleteExperience(experienceId);
    }


    @PostMapping("/{experienceId}/ratings")
    public ResponseEntity<ExperienceRating> rateExperience(@PathVariable Long experienceId, @RequestBody ExperienceRating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.rateExperience(experienceId, rating));
    }

    @PutMapping("/{experienceId}/ratings")
    public ResponseEntity<ExperienceRating> updateExperienceRating(@PathVariable Long experienceId, @RequestBody ExperienceRating rating) {
        return ResponseEntity.ok(experienceService.updateExperienceRating(experienceId, rating));
    }

    @DeleteMapping("/{experienceId}/ratings/{userId}")
    public ResponseEntity<Void> deleteExperienceRating(@PathVariable Long experienceId, @PathVariable Long userId) {
        experienceService.deleteExperienceRating(experienceId, userId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/townships/{townshipId}/experiences")
//    public List<Experience> getExperiencesForTownship(@PathVariable Long townshipId) {
//        return experienceService.getAllExperiences(null, townshipId);
//    }
}
