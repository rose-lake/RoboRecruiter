package com.roselake.jbc;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository <Role, Long> {
    Role findByName(String name);
}
