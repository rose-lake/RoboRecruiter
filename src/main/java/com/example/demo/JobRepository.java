package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface JobRepository extends CrudRepository<Job, Long> {
    ArrayList<Job> findByDescriptionContainingIgnoreCaseOrTitleContainingIgnoreCase(String s, String s1);

    // used for fetching out SPECIFIC jobs in DATA LOADER...
    Job findByTitleContains(String fullTitle);

}
