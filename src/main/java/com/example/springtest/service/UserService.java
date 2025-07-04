package com.example.springtest.service;

import com.example.springtest.model.User;

public interface UserService {
    boolean registerUser(User user);
    User loginUser(String email, String password);
    void forgotPassword(String email);
    void resetPassword(String resetToken, String newPassword);
}
