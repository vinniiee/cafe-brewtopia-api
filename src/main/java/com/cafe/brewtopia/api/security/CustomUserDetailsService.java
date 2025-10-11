package com.cafe.brewtopia.api.security;


import com.cafe.brewtopia.api.entities.Person;
import com.cafe.brewtopia.api.repositories.PersonRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepo) {
        this.personRepository = personRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var authorities = person.getRoles().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(
                person.getEmail(),
                person.getPassword(),
                authorities
        );
    }
}

