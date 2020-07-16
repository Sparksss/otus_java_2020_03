package ru.otus;

import org.hibernate.SessionFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.time.Instant;


public class myCacheMain {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet("+13698541");
        phones.add(pds);
        AddressDataSet address = new AddressDataSet("Time square 543/12");
        User user = new User("Vasya", address, phones);

        long id = dbServiceUser.saveUser(user);

        long end = System.currentTimeMillis();
        System.out.println(end - start);


//        Map<String, Object> week = new WeakHashMap<>();
//        for(int i = 0; i < 100; i++) {
//            week.put(" " + i, "lfmekfmlewmfe");
//        }

//        week.remove(" " + 9);
//        for(int i = 0; i < week.size(); i++) {
//            System.out.println(i);
//            System.out.println(week.get(" " + i));
//        }
    }
}
