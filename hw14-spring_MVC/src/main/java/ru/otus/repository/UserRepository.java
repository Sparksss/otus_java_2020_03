package ru.otus.repository;

import ru.otus.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
    void save(User user);
    void delete(User user);
    User getById(long id);
    void update(User user);
}
