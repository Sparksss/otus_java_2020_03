package ru.otus;

import org.hibernate.SessionFactory;
import ru.otus.cachehw.HWCacheDemo;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.CachedDBServiceUser;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



public class MyCacheMain {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet("+13698541");
        phones.add(pds);
        AddressDataSet address = new AddressDataSet("Time square 543/12");
        User user = new User("Vasya", address, phones);

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        HwCache<Long, User> cache = new MyCache();

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser cachedDBServiceUser = new CachedDBServiceUser(userDao, cache);

        HwListener<Long, User> listener = new HwListener<Long, User>() {
            @Override
            public void notify(Long key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };


        cachedDBServiceUser.addListenerToCache(listener);

        long id = cachedDBServiceUser.saveUser(user);
        User user1 = cachedDBServiceUser.getUser(id).get();
    }
}
