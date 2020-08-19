package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

public interface UserDao {
    User findById(long id);

    User findByName(String login);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
