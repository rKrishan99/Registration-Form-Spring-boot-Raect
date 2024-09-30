package edu.example.demo_app.service;

import edu.example.demo_app.dto.LoginRequest;
import edu.example.demo_app.dto.RegisterRequest;
import edu.example.demo_app.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}