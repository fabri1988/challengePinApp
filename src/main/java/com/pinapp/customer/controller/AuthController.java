package com.pinapp.customer.controller;


import com.pinapp.customer.dto.RegisterRequest;
import com.pinapp.customer.model.User;
import com.pinapp.customer.repository.UserRepository;
import com.pinapp.customer.security.JwtUtil;
import com.pinapp.customer.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"))
        );
        UserDetails user = userDetailsService.loadUserByUsername(body.get("username"));
        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("The user name already exists");
        }

        User nuevo = new User();
        nuevo.setUsername(request.getUsername());
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(nuevo);

        return ResponseEntity.ok("User successfully registered");
    }
}