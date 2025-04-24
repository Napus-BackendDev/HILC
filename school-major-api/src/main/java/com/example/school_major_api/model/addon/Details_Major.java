package com.example.school_major_api.model.addon;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Details_Major {
    private String description;
    private String program;
    private long fee;

    public Details_Major() {
        // Default constructor
    }

    public Details_Major(String description, String program, long fee) {
        this.description = description;
        this.program = program;
        this.fee = fee ;
    }
}
