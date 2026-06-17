package com.example.store;

public interface UserRepository {
    public void save(User user);

    User findByEmail(String email);
}
