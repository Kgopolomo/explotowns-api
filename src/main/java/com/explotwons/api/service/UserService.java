package com.explotwons.api.service;

import com.explotwons.api.entity.PasswordResetToken;
import com.explotwons.api.entity.SavedExperience;
import com.explotwons.api.entity.User;
import com.explotwons.api.reponse.AuthenticationResponse;
import com.explotwons.api.repository.PasswordResetTokenRepository;
import com.explotwons.api.repository.SavedExperienceRepository;
import com.explotwons.api.repository.UserRepository;
import com.explotwons.api.request.Login;
import com.explotwons.api.request.PasswordReset;
import com.explotwons.api.request.Register;
import com.explotwons.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedExperienceRepository savedExperienceRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(Register user) {
        // Check if user with the provided username or email already exists
        if (userRepository.existsByUsername(user.getUsername()) ||
                userRepository.existsByEmail(user.getUsername())) {
            throw new RuntimeException("User with given username or email already exists");
        }

        User newUser = new User();
        newUser.setFullName(user.getFirstName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public AuthenticationResponse login(Login login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = loadUserByUsername(login.getUsername());
        return new AuthenticationResponse(jwtUtil.generateToken(userDetails));
    }

    public boolean requestPasswordReset(PasswordReset passwordReset) {
        User user = userRepository.findByEmail(passwordReset.getEmail()).orElse(null);
        if (user == null) {
            return false; // No user associated with that email address
        }

        // 2. Generate a unique reset token
        String token = UUID.randomUUID().toString();

        // 3. Save the reset token in the database associated with the user
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600_000)); // 1 hour expiry time
        passwordResetTokenRepository.save(resetToken);

        return true;
    }

    public boolean resetPassword(String newPassword, String token) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        if (!resetToken.isPresent()) {
            return false; // Token not found
        }

        //Check if the token has expired
        Date now = new Date();
        if (resetToken.get().getExpiryDate().before(now)) {
            return false; // Token has expired
        }
        // 3. Find the user associated with the token
        User user = resetToken.get().getUser();
        if (user == null) {
            return false; // User not found for the given token
        }

        // 4. Update the user's password with the new password
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        // 5. Invalidate (or delete) the token so it can't be reused
        passwordResetTokenRepository.delete(resetToken.get());

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!userRepository.existsByUsername(username)){
            throw new RuntimeException("User with given username does no exists");
        }
        Optional<User> userProfile = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(userProfile.get().getUsername(), userProfile.get().getPassword(),
                new ArrayList<>());
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<SavedExperience> getSavedExperiences(Long userId) {
        return savedExperienceRepository.findByUserId(userId);
    }

    public SavedExperience saveExperience(Long userId, Long experienceId) {
        // You'd typically fetch the full experience details here. For brevity, I'm just creating a placeholder.
        SavedExperience experience = new SavedExperience();
        experience.setExperienceId(experienceId);
        experience.setTitle("Placeholder Title");
        experience.setDescription("Placeholder Description");
        experience.setUser(userRepository.findById(userId).orElse(null));
        return savedExperienceRepository.save(experience);
    }

    public void deleteExperience(Long userId, Long experienceId) {
        SavedExperience experience = savedExperienceRepository.findByUserIdAndExperienceId(userId, experienceId).orElse(null);
        if (experience != null) {
            savedExperienceRepository.delete(experience);
        }
    }

}

