package com.example.school_major_api.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.school_major_api.Repository.SchoolRepository;
import com.example.school_major_api.model.School;
import com.example.school_major_api.model.addon.Details_School;
import com.example.school_major_api.model.addon.Name;

@RestController
public class Schoolcontroller {

    @Autowired
    private SchoolRepository schoolrepo;

    // Endpoint to create a new school
    @PostMapping("/school")
    public ResponseEntity<?> createSchool(@RequestBody School school) {

        // Check if the Name already exist
        Name name = school.getName();
        List<School> existingSchools = schoolrepo.findByName(name);
        if (!existingSchools.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("School already exists with the same name.");
        }

        // Check if the details already exist
        Details_School details = school.getDetails();
        List<School> existingDetails = schoolrepo.findByDetails(details);
        if (!existingDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("School already exists with the same details.");
        }

        // Check if the acronym already exists
        String acronym = school.getAcronym();
        List<School> existingAcronym = schoolrepo.findByAcronym(acronym);
        if (!existingAcronym.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("School already exists with the same acronym.");
        }

        // Check if the details are null
        if (school.getDetails() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Details cannot be null.");
        }

        // Check if the name is null
        if (school.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be null.");
        }

        // Check if the acronym is null
        if (school.getAcronym() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Acronym cannot be null.");
        }

        // If no conflicts, save the new school
        return ResponseEntity.ok(schoolrepo.save(school)); 
    }

    // Endpoint to retrieve all schools
    @GetMapping("/school")
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(schoolrepo.findAll());
    }

    // Search for a school by useing ( GET /school/search?name=ABAC )
    @GetMapping("/school/search")
    public ResponseEntity<?> searchSchools(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String acronym)
            {
        
        // Search by name
        if (name != null){
            List<School> schoolsThai = schoolrepo.findByName_Thai(name);
            List<School> schoolsEng = schoolrepo.findByName_Eng(name);

            List<School> Name = new ArrayList<>();
            Name.addAll(schoolsThai);
            Name.addAll(schoolsEng);

            if (!Name.isEmpty()) {
                return ResponseEntity.ok(Name);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No school found with the given name.");
            }
        }
        
        // Search by acronym
        else if (acronym != null) {
            List<School> School = schoolrepo.findByAcronym(acronym);
            if (!School.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(School); 
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No school found with the given acronym.");
            }
        } 
        
        return ResponseEntity.badRequest().body("Invalid search parameters.");
    }

    @GetMapping("/school/{id}")
    public ResponseEntity<?> getSchoolById(@PathVariable UUID id) {
        Optional<School> school = schoolrepo.findById(id);
        if (school.isPresent()) {
            return ResponseEntity.ok(school.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");
        }
    }

    //Edit a school
    @PutMapping("/school/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable UUID id  , @RequestBody School school) {
        Optional<School> existingSchool = schoolrepo.findById(id);
        if (existingSchool.isPresent()) {
            School updatedSchool = existingSchool.get();
            updatedSchool.setName(school.getName());
            updatedSchool.setDetails(school.getDetails());
            updatedSchool.setAcronym(school.getAcronym());
            updatedSchool.onUpdate(); 
            return ResponseEntity.status(HttpStatus.OK).body(schoolrepo.save(updatedSchool));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");
        }
    }

    //Delete a school
    @DeleteMapping("/school/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable UUID id) {
        Optional<School> existingSchool = schoolrepo.findById(id);
        if (existingSchool.isPresent()) {
            schoolrepo.delete(existingSchool.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("School deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School not found.");
        }
    }
}
