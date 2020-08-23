package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.List;

public interface DBServiceUser {

    long saveUser(User user);

    User getUserById(long id);

    User getUserByName(String name);

    List<User> getAllUsers();

}
