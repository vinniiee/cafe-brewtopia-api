package com.cafe.brewtopia.api.services;

import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.repositories.PersonRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    public Person getPersonByEmail(String email) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(email);
        if(!person.isPresent()){
            throw new UsernameNotFoundException("User not found with email : "+email);
        }
        return person.get();
    }

}
