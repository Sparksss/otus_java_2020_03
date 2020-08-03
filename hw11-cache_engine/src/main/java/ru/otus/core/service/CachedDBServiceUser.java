package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class CachedDBServiceUser implements DBServiceUser {

    private final UserDao userDao;
    private final HwCache<String, User> cache;

    public CachedDBServiceUser(UserDao userDao, HwCache<String, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    private static final Logger logger = LoggerFactory.getLogger(CachedDBServiceUser.class);

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                long userId = user.getId();
                sessionManager.commitSession();
                cache.put(Long.toString(userId), user);
                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        Optional<User> value = Optional.ofNullable((User) cache.get(Long.toString(id)));
        if(value.isEmpty()) {
            try (SessionManager sessionManager = userDao.getSessionManager()) {
                sessionManager.beginSession();
                try {
                    Optional<User> userOptional = userDao.findById(id);
                    logger.info("user: {}", userOptional.orElse(null));
                    return userOptional;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    sessionManager.rollbackSession();
                }
            }
        }
        return value;
    }
}
