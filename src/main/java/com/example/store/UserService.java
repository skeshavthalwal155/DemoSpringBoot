package com.example.store;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void registerUser(User user) throws IllegalAccessException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalAccessException("User with Email " + user.getEmail() + " is already exists");
        }

        userRepository.save(user);
        notificationService.send("You registered successfully", user.getEmail());
    }
}
