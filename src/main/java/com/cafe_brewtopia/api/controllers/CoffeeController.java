package com.cafe_brewtopia.api.controllers;

import com.cafe_brewtopia.api.entities.Coffee;
import com.cafe_brewtopia.api.services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @GetMapping
    public ResponseEntity<List<Coffee>> getCoffees(){
        List<Coffee> coffees = coffeeService.getAllCoffees();
        return new ResponseEntity<>(coffees, HttpStatus.OK);
    }

}
