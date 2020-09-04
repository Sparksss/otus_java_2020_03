package ru.otus.services;

import ru.otus.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User save(User user);
    void editUser(User user);
}
