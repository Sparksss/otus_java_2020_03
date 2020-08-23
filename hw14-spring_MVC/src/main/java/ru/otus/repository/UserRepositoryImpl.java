package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    UserDao userDao;

    @Override
    public List<User> findAll() {
        SessionManager sessionManager = userDao.getSessionManager();
        Session session = sessionManager.getSession();
        List<User> users = session.createQuery("select * from users", User.class).getResultList();
        return users;
    }
}
