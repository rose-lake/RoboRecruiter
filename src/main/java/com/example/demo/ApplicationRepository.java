package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    //custom query methods, if any, go here
}
