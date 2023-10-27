package com.explotwons.api.controller;

import com.explotwons.api.entity.Experience;
import com.explotwons.api.entity.Rating;
import com.explotwons.api.entity.Township;
import com.explotwons.api.service.TownshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/townships")
public class TownshipController {

    @Autowired
    private TownshipService townshipService;

    @GetMapping
    public ResponseEntity<List<Township>> getAllTownships(@RequestParam(required = false) String province) {
        return ResponseEntity.ok(townshipService.getAllTownships(province));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Township>> getTopRatedTownships() {
        List<Township> topRatedTownships = townshipService.getTopRatedTownships();
        return ResponseEntity.ok(topRatedTownships);
    }

    @PostMapping
    public ResponseEntity<Township> addTownship(@RequestBody Township township) {
        return ResponseEntity.status(HttpStatus.CREATED).body(townshipService.addTownship(township));
    }

    @GetMapping("/{townshipId}")
    public ResponseEntity<Township> getTownshipById(@PathVariable Long townshipId) {
        return ResponseEntity.ok(townshipService.getTownshipById(townshipId));
    }

    @PutMapping("/{townshipId}")
    public ResponseEntity<Township> updateTownship(@PathVariable Long townshipId, @RequestBody Township township) {
        return ResponseEntity.ok(townshipService.updateTownship(townshipId, township));
    }

    @DeleteMapping("/{townshipId}")
    public ResponseEntity<Void> deleteTownship(@PathVariable Long townshipId) {
        townshipService.deleteTownship(townshipId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{townshipId}/ratings")
    public ResponseEntity<Rating> rateTownship(@PathVariable Long townshipId, @RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(townshipService.rateTownship(townshipId, rating));
    }

    @PutMapping("/{townshipId}/ratings")
    public ResponseEntity<Rating> updateRating(@PathVariable Long townshipId, @RequestBody Rating rating) {
        return ResponseEntity.ok(townshipService.updateRating(townshipId, rating));
    }

    @DeleteMapping("/{townshipId}/ratings/{userId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long townshipId, @PathVariable Long userId) {
        townshipService.deleteRating(townshipId, userId);
        return ResponseEntity.noContent().build();
    }
}
