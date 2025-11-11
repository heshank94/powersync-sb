package com.example.powersync_java.repo;

/**
 * @author Heshan Karunaratne
 */

import com.example.powersync_java.jpa.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    boolean existsByUsername(String username);

    Optional<AuthUser> findByUsername(String username);
}