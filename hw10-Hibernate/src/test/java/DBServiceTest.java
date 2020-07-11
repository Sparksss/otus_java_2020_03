import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.hibernate.SessionFactory;
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


public class DBServiceTest {
    private SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.test.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
    private SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

    @Test
    public void createNewUserWithoutPhone() {
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        final String name = "Donald";
        final String address = "Fantastic st 42";

        User user = new User(name, address);
        long id = dbServiceUser.saveUser(user);
        Optional<User> findedUser = dbServiceUser.getUser(id);
        User createdUser = findedUser.get();
        Assertions.assertEquals(name, createdUser.getName());
        Assertions.assertEquals(address, createdUser.getStreet());
    }

    @Test
    public void createNewUserWithPhone() {
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        final String name = "Jimmy";
        final String address = "Amazing st 42";
        final String phone = "+73657894123";

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet(phone);
        phones.add(pds);
        User user = new User(name, address, phones);
        long id = dbServiceUser.saveUser(user);
        Optional<User> finedUser = dbServiceUser.getUser(id);
        User createdUser = finedUser.get();
        Assertions.assertEquals(name, createdUser.getName());
        Assertions.assertEquals(address, createdUser.getStreet());
        String phoneCreatedUser = createdUser.getPhones().get(0).getNumber();
        Assertions.assertEquals(phone, phoneCreatedUser);
    }


    @Test
    public void createUserWithPhoneAndChange() {
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        final String name = "Jimmy";
        final String address = "Amazing st 42";
        final String phone = "+73657894123";

        List<PhoneDataSet> phones = new ArrayList<>();
        PhoneDataSet pds = new PhoneDataSet(phone);
        phones.add(pds);
        User user = new User(name, address, phones);
        long id = dbServiceUser.saveUser(user);
        Optional<User> finedUser = dbServiceUser.getUser(id);
        User createdUser = finedUser.get();
        PhoneDataSet oldPhone = createdUser.getPhones().get(0);
        oldPhone.setNumber("+77777777777");
        dbServiceUser.saveUser(createdUser);
        Assertions.assertEquals(name, createdUser.getName());
        Assertions.assertEquals(address, createdUser.getStreet());
        String phoneCreatedUser = createdUser.getPhones().get(0).getNumber();
        Assertions.assertNotEquals(phone, phoneCreatedUser);
    }
}
