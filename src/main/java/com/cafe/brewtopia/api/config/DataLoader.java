package com.cafe.brewtopia.api.config;

import com.cafe.brewtopia.api.entities.Coffee;
import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.enums.Role;
import com.cafe.brewtopia.api.repositories.CoffeeRepository;
import com.cafe.brewtopia.api.repositories.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CoffeeRepository coffeeRepository;
    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        loadCoffeeData();
        createDefaultUsers();
    }

    private void loadCoffeeData() throws Exception {
        if (coffeeRepository.count() == 0) {
            try (InputStream inputStream =
                         getClass().getResourceAsStream("/data/coffees.json")) {

                List<Coffee> coffees = objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<Coffee>>() {}
                );

                coffeeRepository.saveAll(coffees);
                System.out.println("✅ Coffee data loaded successfully!");
            }
        } else {
            System.out.println("☕ Coffee data already exists, skipping load.");
        }
    }

    private void createDefaultUsers() {
        if (personRepository.count() == 0) {
            Person user1 = Person.builder()
                    .name("John Doe")
                    .email("john@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .roles(Set.of(Role.ROLE_USER))
                    .address("123 Main Street")
                    .build();

            Person user2 = Person.builder()
                    .name("Jane Doe")
                    .email("jane@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .roles(Set.of(Role.ROLE_USER))
                    .address("456 Elm Street")
                    .build();

            Person admin = Person.builder()
                    .name("Admin User")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .address("789 Oak Avenue")
                    .build();

            personRepository.saveAll(List.of(user1, user2, admin));
            System.out.println("✅ Default users created successfully!");
        } else {
            System.out.println("👤 Users already exist, skipping creation.");
        }
    }
}
