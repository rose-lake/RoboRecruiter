package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    //custom query methods, if any, go here
    Link findByNameContains(String fullName);
}
