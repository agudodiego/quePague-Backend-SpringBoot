package com.agudodiego.quePague.repository;

import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DataSeeder {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    String username;
    @Value("${admin.password}")
    String password;
    @Value("${admin.email}")
    String email;

    @EventListener
    public void seed(ContextRefreshedEvent event) {

        if (!personRepository.existsByUsername(username)) {
            Person admin = Person.builder()
                    .username(username)
                    .pass(passwordEncoder.encode(password))
                    .email(email)
                    .role(Role.ADMIN)
                    .build();
            personRepository.save(admin);
        }
    }
}
