
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

import com.example.school_major_api.Repository.MajorRepository;
import com.example.school_major_api.model.Major;
import com.example.school_major_api.model.addon.Name;


@RestController
public class MajorController {
    
    @Autowired
    private MajorRepository majorRepo;

    // Endpoint to create a new major
    @PostMapping("/major")
    public ResponseEntity<?> createMajor(@RequestBody Major major) {

        // Check ID of the school is valid or not
        UUID Id = major.getSchool().getId();
        Optional<Major> exisitngId = majorRepo.findById(Id);
        if (exisitngId.isPresent()){
            return ResponseEntity.badRequest().body("Invaild School Id");
        }
        
        // Check if the major already exists
        Name name = major.getName();
        List<Major> existingName = majorRepo.findByName(name);
        if (!existingName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("School already exists with the same name.");
        }

        // Check if the acronym already exists
        String acronym = major.getAcronym();
        List<Major> existingAcronym = majorRepo.findByAcronym(acronym);
        if (!existingAcronym.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("School already exists with the same acronym.");
        }

        // Check if the Id are null
        if (major.getSchool().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("School cannot be null.");
        }

        // Check if the details are null
        if (major.getDetails() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Details cannot be null.");
        }

        // Check if the name is null
        if (major.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be null.");
        }

        // Check if the acronym is null
        if (major.getAcronym() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Acronym cannot be null.");
        }

        // If no conflicts, save the new Major
        return ResponseEntity.ok(majorRepo.save(major)); 
    }

    // Show major all majors
    @GetMapping("/major")
    public ResponseEntity<Iterable<Major>> getAllMajors() {
        return ResponseEntity.ok(majorRepo.findAll());
    }

    // Search major by useing ( name or acronym )
    // Example: GET /major/search?acronym=ADT 
    @GetMapping("/major/search")
    public ResponseEntity<?> searchMajors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String acronym)
            {
        
        // Search by name
        if (name != null){
            List<Major> majors = majorRepo.findByName_Thai(name);
            List<Major> majorsEng = majorRepo.findByName_Eng(name);

            List<Major> Name = new ArrayList<>();
            Name.addAll(majors);
            Name.addAll(majorsEng);

            if (!Name.isEmpty()) {
                return ResponseEntity.ok(Name);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No majors found with the given name.");
            }
        }

        // Search by acronym
        if (acronym != null){
            List<Major> majors = majorRepo.findByAcronym(acronym);
            if (!majors.isEmpty()) {
                return ResponseEntity.ok(majors);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No majors found with the given acronym.");
            }
        }

        // If no search parameters are provided, return all majors
        return ResponseEntity.badRequest().body("Invalid search parameters.");
    }

    // Get major by id
    @GetMapping("/major/{id}")
    public ResponseEntity<?> getMajorById(@PathVariable UUID id) {
        // Check if the major exists
        Optional<Major> major = majorRepo.findById(id);
        if (!major.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Major not found.");
        }
        return ResponseEntity.ok(major.get());
    }

    // Update or Edit major by id
    @PutMapping("/major/{id}")
    public ResponseEntity<?> updateMajor(@PathVariable UUID id ,@RequestBody Major major) {
        // Check if the major exists
        Optional<Major> existingMajor = majorRepo.findById(id);
        if (existingMajor.isPresent()) {
            Major updatedMajor = existingMajor.get();
            updatedMajor.setName(major.getName());
            updatedMajor.setDetails(major.getDetails());
            updatedMajor.setAcronym(major.getAcronym());
            updatedMajor.setSchool(major.getSchool());
            updatedMajor.onUpdate();

            // Save the updated major
            return ResponseEntity.ok(majorRepo.save(updatedMajor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Major not found.");
        }
    }

    // Delete major by id
    @DeleteMapping("/major/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable UUID id) {
        // Check if the major exists
        Optional<Major> existingMajor = majorRepo.findById(id);
        if (existingMajor.isPresent()) {
            majorRepo.delete(existingMajor.get());
            return ResponseEntity.status(HttpStatus.OK).body("Major deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Major not found.");
        }
    }
}
