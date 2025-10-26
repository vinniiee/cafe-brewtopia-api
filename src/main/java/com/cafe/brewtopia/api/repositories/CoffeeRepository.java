package com.cafe.brewtopia.api.repositories;

import com.cafe.brewtopia.api.entities.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {


    List<Coffee> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String term, String term1);
}
