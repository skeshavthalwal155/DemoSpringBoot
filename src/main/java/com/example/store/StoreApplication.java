package com.example.store;

import com.example.store.entities.Address;
import com.example.store.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
//        SpringApplication.run(StoreApplication.class, args);

        var user = User.builder()
                .name("Keshav")
                .password("1234")
                .email("keshav@gmail.com")
                .build();

        user.addTag("tag1");

        System.out.println(user);
    }
}
