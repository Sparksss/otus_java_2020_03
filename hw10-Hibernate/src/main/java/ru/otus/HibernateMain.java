package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.core.dao.UserDao;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.core.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HibernateMain {
    private static Logger logger = LoggerFactory.getLogger(HibernateMain.class);

    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet("+13698541");
        phones.add(pds);
        User user = new User("Vasya", "Time square 543/12", phones);

        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);

        user.setName("John");

        dbServiceUser.saveUser(user);

        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);
        outputUserOptional("Updated user", mayBeUpdatedUser);


        List<PhoneDataSet> phones2 = new ArrayList<>();
        PhoneDataSet pds2 = new PhoneDataSet("+79151711514");
        PhoneDataSet pds3 = new PhoneDataSet("+4587963217");
        phones2.add(pds2);
        phones2.add(pds3);

        User user1 = new User("Jimmy", "Walker st 157", phones2);

        long id1 = dbServiceUser.saveUser(user1);

        Optional<User> mayBeCreatedUser1 = dbServiceUser.getUser(id1);

        outputUserOptional("Created user", mayBeCreatedUser1);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
