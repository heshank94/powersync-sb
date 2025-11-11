package com.example.powersync_java.jpa;

/**
 * @author Heshan Karunaratne
 */

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lists")
public class Lists {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(length = 100)
    private String name;

    @Column(name = "owner_id", length = 36)
    private String ownerId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
