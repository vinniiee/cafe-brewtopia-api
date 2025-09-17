package com.cafe_brewtopia.api.repositories;

import com.cafe_brewtopia.api.entities.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {



}
