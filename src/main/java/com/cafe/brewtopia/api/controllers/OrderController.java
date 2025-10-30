package com.cafe.brewtopia.api.controllers;

import com.cafe.brewtopia.api.entities.Order;
import com.cafe.brewtopia.api.services.OrderService;
import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.repositories.PersonRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PersonRepository personRepository;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order,
                                        @AuthenticationPrincipal UserDetails person) {
//        order.setPersonId(Long.parseLong(person.getUsername()));
        System.out.println("trying to place order for Order body: "+order.toString()+"\n Person: "+person.toString());
        try{
            Order savedOrder = orderService.placeOrder(person,order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal UserDetails personDetails) {
        try {
            List<Order> orders = orderService.getOrdersByPerson(personDetails);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch orders: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}