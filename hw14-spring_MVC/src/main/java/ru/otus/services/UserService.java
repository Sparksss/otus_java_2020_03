package ru.otus.services;

import ru.otus.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User save(User user);
    void update(int id, String name);
    void deleteUser(int id);
}
