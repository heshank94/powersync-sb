package com.example.powersync_java.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LibraryController {

    @GetMapping("/hello/")
    public String hello() {
        System.out.println("hello endpoint");
        return "Public endpoint";
    }

    @GetMapping("/profile/")
    public String userProfile(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("profile endpoint");
        return "Hello " + jwt.getClaimAsString("preferred_username") +
                " | Roles: " + jwt.getClaimAsStringList("frappe_roles");
    }

    @PreAuthorize("hasRole('Librarian')")
    @GetMapping("/auth/")
    public String librarianOnly() {
        System.out.println("auth endpoint");
        return "Accessible only to Librarian role";
    }

    @PreAuthorize("!hasRole('Librarian')")
    @GetMapping("/nonauth/")
    public String nonLibrarianOnly() {
        System.out.println("nopnauth endpoint");
        return "Accessible to anyone except Librarian";
    }
}
