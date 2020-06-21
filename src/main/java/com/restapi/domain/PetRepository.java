package com.restapi.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<Pet,Long> {
    List<Pet> findAll();
}
