package com.example.school_major_api.Repository;

import com.example.school_major_api.model.addon.Name;
import com.example.school_major_api.model.School;
import com.example.school_major_api.model.addon.Details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface SchoolRepository extends JpaRepository<School, UUID> {

    List<School> findByName_Thai(String thai);
    List<School> findByName_Eng(String eng);
    List<School> findByName(Name name);
    List<School> findByDetails(Details details);
    List<School> findByAcronym(String acronym);

}