package com.example.springtest.controller;

import com.example.springtest.model.User;
import com.example.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        boolean registered = userService.registerUser(user);
        if (registered) {
            response.put("status", "success");
            response.put("message", "Registration successful!");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Registration failed: Email already exists or error occurred.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        User user = userService.loginUser(email, password);
        if (user != null) {
            response.put("status", "success");
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        userService.forgotPassword(email);
        response.put("status", "success");
        response.put("message", "Reset link sent (mock). Check logs or email.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String token, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        userService.resetPassword(token, password);
        response.put("status", "success");
        response.put("message", "Password reset successful.");
        return ResponseEntity.ok(response);
    }
}
