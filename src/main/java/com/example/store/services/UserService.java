package com.example.store.services;

import com.example.store.entities.Address;
import com.example.store.entities.User;
import com.example.store.repositories.AddressRepository;
import com.example.store.repositories.ProfileRepository;
import com.example.store.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository ProfileRepository;
    private final EntityManager entityManager;
    private final AddressRepository addressRepository;

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
    @Transactional
        public void showRelatedEntities(){
            var profile = ProfileRepository.findById(2L).orElseThrow();
            System.out.println(profile.getUser().getEmail());
        }

        public void fetchAddress(){
           var address = addressRepository.findById(1L).orElseThrow();
        }

        public void presistRelated(){
            var user = User.builder()
                    .name("John Doe")
                    .email("john.doe@example.com")
                    .password("password")
                    .build();
            var address = Address.builder()
                    .street("street")
                    .city("city")
                    .state("state")
                    .zip("zip")
                    .build();

            user.addAddress(address);

            userRepository.save(user);
        }

        public void deleteRelated(){
        userRepository.deleteById(1L);
        }

}
