package ru.otus.repository;

import ru.otus.core.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
