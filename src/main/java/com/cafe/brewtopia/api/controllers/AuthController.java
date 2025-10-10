package com.cafe.brewtopia.api.controllers;

import com.cafe.brewtopia.api.dtos.AuthResponse;
import com.cafe.brewtopia.api.dtos.LoginRequest;
import com.cafe.brewtopia.api.dtos.RegisterRequest;
import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.enums.Role;
import com.cafe.brewtopia.api.repositories.PersonRepository;
import com.cafe.brewtopia.api.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final PersonRepository personRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          PersonRepository personRepo,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
        if (personRepo.existsByUsername(req.username()))
            return ResponseEntity.badRequest().body("Username already taken");
        if (personRepo.existsByEmail(req.email()))
            return ResponseEntity.badRequest().body("Email already in use");

        Person user = new Person();
        user.setName(req.name());
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setRoles(Set.of(Role.ROLE_USER));
        personRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username(), req.password())
            );

            var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            Set<String> roles = principal.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toSet());

            String token = jwtUtil.generateToken(principal.getUsername(), roles);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
