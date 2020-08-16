package ru.otus.services;

import ru.otus.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User findByName(String name);
    User save(User user);
}
