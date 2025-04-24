package com.example.school_major_api.model.addon;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class Name {                         

    // Name in Thai and English
    public String thai;
    public String eng;

    public Name() {
    }

    public Name(String thai, String eng) {
        this.thai = thai;
        this.eng = eng;
    }
    
}

