package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume, Long> {
    // custom query methods here
    Resume findByNameContains(String fullTitle);
}
