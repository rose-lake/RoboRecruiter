package com.roselake.jbc;

import org.springframework.data.repository.CrudRepository;

public interface InterviewRepository extends CrudRepository<Interview, Long> {
    //custom query methods, if any, go here
    Interview findByLink(Link link);
}
