package com.example.school_major_api.model;
import com.example.school_major_api.model.addon.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Details_School details;

    private String acronym;

    @Column(name = "Created_At", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , updatable = false)
    @JsonFormat(pattern = "dd MMMM yyyy, 'เวลา' HH:mm ", timezone = "Asia/Bangkok")
    private Instant createdAt;

    @Column(name = "Updated_At", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , updatable = false)
    @JsonFormat(pattern = "dd MMMM yyyy, 'เวลา' HH:mm ", timezone = "Asia/Bangkok")
    private Instant updatedAt;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Major> majors;

    public School() {
        // Default constructor
    }
    
    public School(UUID id, Name name, Details_School details, String acronym) {
        this.id = id;
        this.details = details;
        this.name = name;
        this.acronym = acronym;
        onCreate();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
    
}
