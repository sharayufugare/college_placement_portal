package com.example.placementportal.controller;

import com.example.placementportal.model.Student;
import com.example.placementportal.security.JwtUtil;
import com.example.placementportal.service.StudentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final StudentService studentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        Student student = studentService
                .login(req.getEmail(), req.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Check domain restriction
        if (!req.getEmail().endsWith("@pccoer.in")) {
            throw new RuntimeException("Use official college email only!");
        }

        String token = jwtUtil.generateToken(req.getEmail());

        return new LoginResponse(student.getId(), token);
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class LoginResponse {
        private Long studentId;
        private String token;

        public LoginResponse(Long id, String token) {
            this.studentId = id;
            this.token = token;
        }
    }
}
