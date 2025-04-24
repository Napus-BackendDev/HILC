package com.example.school_major_api.model;
import com.example.school_major_api.model.addon.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "major")

// The major entity represents a major in a school
public class Major {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Many majors can belong to one school
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    @JsonIgnoreProperties("majors")
    private School school;

    @Embedded
    private Details details;

    @Embedded
    private Name name;

    private String acronym;

    // The first time the entity was created
    @Column(name = "Created_At", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , updatable = false)
    @JsonFormat(pattern = "dd MMMM yyyy, 'เวลา' HH:mm ", timezone = "Asia/Bangkok")
    private Instant createdAt;

    // The last time the entity was updated
    @Column(name = "Updated_At", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , updatable = false)
    @JsonFormat(pattern = "dd MMMM yyyy, 'เวลา' HH:mm ", timezone = "Asia/Bangkok")
    private Instant updatedAt;

    public Major() {
        // Default constructor
    }

    // Constructor
    public Major(UUID id, School school , Details details, Name name, String acronym) {
        this.id = id;
        this.school = school;
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
