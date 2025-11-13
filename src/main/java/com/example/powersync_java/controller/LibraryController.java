package com.example.powersync_java.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LibraryController {


    @GetMapping("/profile/")
    public String userProfile(@AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println("/user/profile endpoint");
        System.out.println("oidcUser:"+ oidcUser);
        return "Hello " + oidcUser.getFullName() + " | Roles: " + oidcUser.getClaimAsStringList("frappe_roles");
    }

    @PreAuthorize("hasRole('Librarian')")
    @GetMapping("/auth/")
    public String librarianOnly() {
        System.out.println("lib endpoint");
        return "Accessible only to Librarian role";
    }

    @PreAuthorize("!hasRole('Librarian')")
    @GetMapping("/nonauth/")
    public String nonLibrarianOnly() {
        System.out.println("nonlib endpoint");
        return "Accessible to anyone except Librarian";
    }
}
