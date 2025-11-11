package com.example.powersync_java.jpa;

/**
 * @author Heshan Karunaratne
 */

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todos")
public class Todos {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(columnDefinition = "text")
    private String description;

    private boolean completed;

    @Column(name = "created_by", length = 36)
    private String createdBy;

    @Column(name = "completed_by", length = 36)
    private String completedBy;

    @Column(name = "list_id", length = 36)
    private String listId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
