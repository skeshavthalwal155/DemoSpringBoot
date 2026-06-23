package com.example.store.services;

import com.example.store.entities.User;
import com.example.store.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public void showEntityStates(){
        var user = User.builder()
                .name("John Doe")
                .email("John.doe@example.com")
                .password("password")
                .build();

        if (entityManager.contains(user)){
            System.out.println("Persistent");
        }else System.out.println("Transisent / Detached");

        userRepository.save(user);

        if (entityManager.contains(user)){
            System.out.println("Persistent");
        }else{
            System.out.println("Transisent / Detached");
        }
    }
}
