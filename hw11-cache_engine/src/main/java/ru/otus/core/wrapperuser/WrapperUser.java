package ru.otus.core.wrapperuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;

public class WrapperUser {
    private final DBServiceUser dbServiceUser;

    private HwCache<String, User> cache = new MyCache<>();

    public WrapperUser(UserDao userDao) {
        dbServiceUser = new DbServiceUserImpl(userDao);
    }

    private static final Logger logger = LoggerFactory.getLogger(WrapperUser.class);

    public long saveUser(User user) {

        long id = 0;

        try {
            id = dbServiceUser.saveUser(user);
            cache.put("" + id, user);
        } catch (Exception e) {
               logger.error(e.getMessage());
        }
        return id;
    }

    public User getUserById(long id) {
        try {
            User user = (User) cache.get("" + id);
            if(user == null) user = dbServiceUser.getUser(id).get();
            return user;
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void addListenerToCache(HwListener listener) {
        this.cache.addListener(listener);
    }
}
