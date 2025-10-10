package com.cafe.brewtopia.api.services;

import com.cafe.brewtopia.api.entities.Coffee;
import com.cafe.brewtopia.api.repositories.CoffeeRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    CoffeeRepository coffeeRepository;

    public List<Coffee> getAllCoffees(){
        List<Coffee> coffees =  coffeeRepository.findAll();
        return coffees;
    }

    public Coffee addCoffee(Coffee coffee) throws BadRequestException {
        if(coffee==null){
            throw new BadRequestException("Invalid input for coffee.");
        }
        return coffeeRepository.save(coffee);
    }
}
