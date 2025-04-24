package com.example.school_major_api.model.addon;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Details_School {
    private String description;
    private String phoneNumber;
    private String email;

    public Details_School() {
        // Default constructor
    }


    // Constructor for School
    public Details_School( String description, String phoneNumber, String email ) {
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
}
