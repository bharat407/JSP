package com.example.springtest.service;

import com.example.springtest.model.User;
import com.example.springtest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                logger.error("Registration failed: Email already exists - {}", user.getEmail());
                return false;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            logger.info("User registered successfully: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return false;
        }
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            logger.info("User logged in successfully: {}", email);
            return user;
        }
        logger.warn("Login failed for user: {}", email);
        return null;
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);
            // In a real application, you would send an email with the reset token.
            // For this example, we'll just log it.
            logger.info("Password reset token for user {}: {}", email, token);
        }
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            userRepository.save(user);
            logger.info("Password reset successfully for user: {}", user.getEmail());
        }
    }
}