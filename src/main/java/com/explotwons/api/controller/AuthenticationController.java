package com.explotwons.api.controller;

import com.explotwons.api.request.Login;
import com.explotwons.api.request.PasswordReset;
import com.explotwons.api.request.PasswordUpdateRequest;
import com.explotwons.api.request.Register;
import com.explotwons.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Register user) {
        userService.register(user);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login user) throws Exception {
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // In JWT, there's no server-side way to invalidate a token.
        // Simply remove the token on the client-side to "logout".
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordReset passwordReset) {
        if (userService.requestPasswordReset(passwordReset)) {
            return ResponseEntity.ok("Password reset link sent to email");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/password/update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequest request) {
        if (userService.resetPassword(request.getNewPassword(), request.getResetToken())) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(401).body("Unauthorized - invalid reset token");
        }
    }
}
