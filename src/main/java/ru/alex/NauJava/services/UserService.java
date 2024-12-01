package ru.alex.NauJava.services;

import ru.alex.NauJava.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);

    User addUser(String username, String password);
}
