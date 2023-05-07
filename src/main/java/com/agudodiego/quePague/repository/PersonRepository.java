package com.agudodiego.quePague.repository;

import com.agudodiego.quePague.model.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Boolean existsByUsername(String username);
    Optional<Person> findByUsername(String username);
}
