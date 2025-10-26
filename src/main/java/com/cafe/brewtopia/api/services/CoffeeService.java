package com.cafe.brewtopia.api.services;

import com.cafe.brewtopia.api.entities.Coffee;
import com.cafe.brewtopia.api.repositories.CoffeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Fetch all coffees (existing method)
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    // Add a new coffee (existing method)
    public Coffee addCoffee(Coffee coffee) throws BadRequestException {
        if (coffee == null) {
            throw new BadRequestException("Invalid input for coffee.");
        }
        return coffeeRepository.save(coffee);
    }

    // Search coffees by term (name or description)
    public List<Coffee> searchCoffees(String term) {
        if (term == null || term.isEmpty()) {
            return coffeeRepository.findAll();
        }
        return coffeeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(term, term);
    }

    // Filter and sort coffees
    public List<Coffee> getCoffeesWithSearchFiltersAndSort(
            String term, String type, List<String> attributes, String sortBy, Boolean ascending) {

        // base query (search or full list)
        List<Coffee> coffees;
        if (term != null && !term.isEmpty()) {
            coffees = coffeeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(term, term);
        } else {
            coffees = coffeeRepository.findAll();
        }
        System.out.println(coffees);

        // filter by type
        if (type != null && !type.isEmpty()) {
            System.out.println("Filtering by type: "+type);
            coffees = coffees.stream()
                    .filter(c -> (c.getAttributes().stream().anyMatch(i -> i.toLowerCase().equals(type.toLowerCase()))))
                    .collect(Collectors.toList());
            System.out.println("Filtered by type: "+coffees);
        }

        // filter by cascade attributes
        if (attributes != null && !attributes.isEmpty()) {
            System.out.println("Filtering by attributes: "+attributes);
            try {
                for (String attr : attributes) {
                    System.out.println("Filtering for: "+attr);
                    coffees = coffees.stream()
                            .filter(c -> c.getAttributes() != null &&
                                    c.getAttributes().stream().anyMatch(a -> {
                                        System.out.println(a.toLowerCase() + " , " + attr.toLowerCase() + " : "
                                                + a.toLowerCase().equals(attr.toLowerCase()));
                                        return a.toLowerCase().equals(attr.toLowerCase());
                                    }))
                            .collect(Collectors.toList());
                }

            } catch (Exception e) {
                throw new RuntimeException("Invalid attributes JSON: " + e.getMessage());
            }
        }

        // sort if applicable
        if (sortBy != null && !sortBy.isEmpty()) {

            coffees.sort((c1, c2) -> {
                try {
                    Field field = Coffee.class.getDeclaredField(sortBy);
                    field.setAccessible(true);
                    Object val1 = field.get(c1);
                    Object val2 = field.get(c2);

                    if (val1 instanceof Comparable && val2 instanceof Comparable) {
                        Comparable comp1 = (Comparable) val1;
                        Comparable comp2 = (Comparable) val2;
                        return ascending ? comp1.compareTo(comp2) : comp2.compareTo(comp1);
                    }
                    // in case of prices
                    else if (val1 instanceof List<?> l1 && val2 instanceof List<?> l2) {
                        Comparable a = (Comparable) l1.get(0);
                        Comparable b = (Comparable) l2.get(0);
                        return ascending ? a.compareTo(b) : b.compareTo(a);
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    // ignore field not found
                }
                return 0;
            });
        }

        return coffees;
    }
}
