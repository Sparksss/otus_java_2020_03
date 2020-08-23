package ru.otus.core.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.DatabaseSession;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.dao.UserDaoHibernate;

@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    @Getter
    private final SessionManager sessionManager;

    @Override
    public User findById(long id) {
        User user = null;
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            user = currentSession.getSession().find(User.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User findByName(String login) {
        User user = null;
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            user = currentSession.getSession().find(User.class, login);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public long insertUser(User user) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getSession();
            hibernateSession.persist(user);
            hibernateSession.flush();
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getSession();
            hibernateSession.merge(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(User user) {
        DatabaseSession currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

}
