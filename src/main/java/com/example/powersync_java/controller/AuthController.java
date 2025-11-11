package com.example.powersync_java.controller;

import com.example.powersync_java.config.CustomUserDetails;
import com.example.powersync_java.jpa.AuthUser;
import com.example.powersync_java.repo.AuthUserRepository;
import com.example.powersync_java.request.AuthRequest;
import com.example.powersync_java.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @author Heshan Karunaratne
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/get_session/")
    public ResponseEntity<Map<String, String>> getSession() {
        System.out.println("Calling get_session api");
        return ResponseEntity.ok(Collections.singletonMap("session", "valid"));
    }

    @PostMapping("/auth/")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthRequest request) {
        System.out.println("Calling auth api");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.createJwtToken(userDetails.getId());

            return ResponseEntity.ok(Collections.singletonMap("access_token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Authentication failed"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Internal server error"));
        }
    }

    @PostMapping("/register/")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequest req) {
        System.out.println("Calling register api");
        if (authUserRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Username already exists"));
        }

        AuthUser user = AuthUser.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .firstName("")
                .lastName("")
                .email("")
                .isActive(true)
                .isStaff(false)
                .isSuperuser(false)
                .build();

        authUserRepository.save(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "Registered"));
    }

}
