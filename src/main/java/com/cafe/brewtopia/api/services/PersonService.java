package com.cafe.brewtopia.api.services;

import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.exceptions.ResourceNotFoundException;
import com.cafe.brewtopia.api.repositories.PersonRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Person getPersonByEmail(String email) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(email);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email : " + email);
        }
        return person.get();
    }

    public Person updatePerson(Person person) {
        Person existing = personRepository.findByEmail(person.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + person.getEmail()));

        if (person.getName() != null) {
            existing.setName(person.getName());
        }
        if (person.getAddress() != null) {
            existing.setAddress(person.getAddress());
        }
        if (person.getPassword() != null) {
            existing.setPassword(person.getPassword());
        }
        return personRepository.save(existing);
    }

}
