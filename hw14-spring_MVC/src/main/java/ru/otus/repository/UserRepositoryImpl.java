package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    UserDao userDao;
    DBServiceUser dbServiceUser;

    @Override
    public List<User> findAll() {
        List<User> users = this.dbServiceUser.getAllUsers();
        return users;
    }
}
