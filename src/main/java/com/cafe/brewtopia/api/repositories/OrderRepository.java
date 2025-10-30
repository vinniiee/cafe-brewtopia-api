package com.cafe.brewtopia.api.repositories;

import com.cafe.brewtopia.api.entities.Order;
import com.cafe.brewtopia.api.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByPersonOrderByCreatedAtDesc(Person person);
}
