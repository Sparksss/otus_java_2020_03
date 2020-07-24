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
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



public class myCacheMain {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) {

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet("+13698541");
        phones.add(pds);
        AddressDataSet address = new AddressDataSet("Time square 543/12");
        User user = new User("Vasya", address, phones);

        long start = System.currentTimeMillis();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        long id = dbServiceUser.saveUser(user);

        long end = System.currentTimeMillis();
        logger.info("time has passed after add user in DB, : " + (end - start));


        start = System.currentTimeMillis();
        HwCache<String, User> cache = new MyCache<>();

        HwListener<String, User> listener = new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("" + 1, user);

        logger.info("getValue:{}", cache.get("" + 1));
        cache.remove("" + 1);
        cache.removeListener(listener);
        end = System.currentTimeMillis();

        logger.info("time has passed after add user in Cache : " + (end - start));
    }
}
