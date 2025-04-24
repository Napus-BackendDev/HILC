package com.example.school_major_api.Repository;

import com.example.school_major_api.model.Major;
import com.example.school_major_api.model.addon.Details;
import com.example.school_major_api.model.addon.Name;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface MajorRepository extends JpaRepository <Major, UUID> {
    List<Major> findByName_Thai(String thai);
    List<Major> findByName_Eng(String eng);
    List<Major> findByDetails(Details details);
    List<Major> findByAcronym(String acronym);
    List<Major> findByName(Name name);
    
}
