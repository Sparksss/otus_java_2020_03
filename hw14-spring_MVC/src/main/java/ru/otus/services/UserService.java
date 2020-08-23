package ru.otus.services;

import ru.otus.core.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User findByName(String name);
    User save(User user);
}
