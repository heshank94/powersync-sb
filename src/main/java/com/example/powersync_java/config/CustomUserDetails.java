package com.example.powersync_java.config;

/**
 * @author Heshan Karunaratne
 */

import com.example.powersync_java.jpa.AuthUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final boolean isActive;

    public CustomUserDetails(AuthUser user) {
        this.id = user.getId().longValue();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}