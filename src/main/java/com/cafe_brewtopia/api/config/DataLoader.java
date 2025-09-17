package com.cafe_brewtopia.api.config;

import com.cafe_brewtopia.api.entities.Coffee;
import com.cafe_brewtopia.api.repositories.CoffeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CoffeeRepository coffeeRepository;
    private final ObjectMapper objectMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        if (coffeeRepository.count() == 0) {
            try (InputStream inputStream =
                         getClass().getResourceAsStream("/data/coffees.json")) {

                List<Coffee> coffees = objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<Coffee>>() {}
                );

                coffeeRepository.saveAll(coffees);
                System.out.println("Coffee data loaded successfully!");
            }
        } else {
            System.out.println("Coffee data already exists, skipping load.");
        }
    }
}
