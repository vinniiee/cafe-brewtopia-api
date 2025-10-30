package com.cafe.brewtopia.api.services;

import com.cafe.brewtopia.api.entities.Order;
import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.repositories.OrderRepository;
import com.cafe.brewtopia.api.repositories.PersonRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public Order placeOrder(UserDetails personDetails, Order order) {
        Optional<Person> person = personRepository.findByEmail(personDetails.getUsername());
        if (person.isPresent()) {
            order.setPerson(person.get());
        } else {
            throw new RuntimeException("Person not found using email : " + personDetails.getUsername());
        }
        Order savedOrder = orderRepository.save(order);
        System.out.println("Saved Order : "+savedOrder.toString());
        return savedOrder;
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByPerson(UserDetails personDetails) {
        Optional<Person> person = personRepository.findByEmail(personDetails.getUsername());
        if(person.isPresent()){
            return orderRepository.findByPersonOrderByCreatedAtDesc(person.get());
        }else{
            throw new RuntimeException("Person not found while searching all orders for username: "+personDetails.getUsername());
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}