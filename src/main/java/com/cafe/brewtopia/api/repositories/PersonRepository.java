package com.cafe.brewtopia.api.repositories;

import com.cafe.brewtopia.api.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email);
}