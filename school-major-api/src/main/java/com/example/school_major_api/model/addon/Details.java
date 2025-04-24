package com.example.school_major_api.model.addon;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Details {
    private String description;
    private String phoneNumber;
    private String email;


    public Details() {
        // Default constructor
    }

    // Constructor for Major


    // Constructor for School
    public Details( String description, String phoneNumber, String email) {
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
}
