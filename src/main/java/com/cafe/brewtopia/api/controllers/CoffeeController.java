package com.cafe.brewtopia.api.controllers;

import com.cafe.brewtopia.api.entities.Coffee;
import com.cafe.brewtopia.api.services.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    // GET /coffees with filters and sorting
    @GetMapping
    public ResponseEntity<List<Coffee>> getCoffees(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<String> attributes,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false) String term
    ) {
        System.out.println(
                "type: " + (type != null ? type : "null") +
                        " | attributes: " + (attributes != null && !attributes.isEmpty() ? attributes.toString() : "[]") +
                        " | sortBy: " + (sortBy != null ? sortBy : "null") +
                        " | ascending: " + ascending +
                        " | term: " + (term != null ? term : "null")
        );

        List<Coffee> coffees = coffeeService.getCoffeesWithSearchFiltersAndSort(term, type, attributes, sortBy, ascending);
        return new ResponseEntity<>(coffees, HttpStatus.OK);
    }

}
