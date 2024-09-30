package edu.example.demo_app.service;

import edu.example.demo_app.dto.LoginRequest;
import edu.example.demo_app.dto.RegisterRequest;
import edu.example.demo_app.dto.AuthResponse;
import edu.example.demo_app.exception.EmailAlreadyExistsException;
import edu.example.demo_app.model.User;
import edu.example.demo_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsById(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return new AuthResponse("User registered successfully", true);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("Login successful", true);
        } else {
              throw new RuntimeException("Invalid credentials");
        }
    }
    //            return new AuthResponse("Invalid credentials", false);



}