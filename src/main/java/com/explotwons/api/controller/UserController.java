package com.explotwons.api.controller;

import com.explotwons.api.entity.SavedExperience;
import com.explotwons.api.entity.User;
import com.explotwons.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);  // Ensure the ID is set from the path variable
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}/savedExperiences")
    public ResponseEntity<List<SavedExperience>> getSavedExperiences(@PathVariable Long userId) {
        List<SavedExperience> experiences = userService.getSavedExperiences(userId);
        return ResponseEntity.ok(experiences);
    }

    @PostMapping("/{userId}/savedExperiences")
    public ResponseEntity<SavedExperience> saveExperience(@PathVariable Long userId, @RequestBody Map<String, Long> body) {
        Long experienceId = body.get("experienceId");
        SavedExperience experience = userService.saveExperience(userId, experienceId);
        return ResponseEntity.status(201).body(experience);
    }

    @DeleteMapping("/{userId}/savedExperiences/{experienceId}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long userId, @PathVariable Long experienceId) {
        userService.deleteExperience(userId, experienceId);
        return ResponseEntity.ok().build();
    }
}
