package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.model.Account;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        var demo = new DbServiceDemo();

        demo.createTableUser(dataSource);
        demo.createTableAccount(dataSource);

        User user = new User(0, "dbServiceUser", 0);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();

        var jdbcMapper = new JdbcMapperImpl(dbExecutor, sessionManager, user.getClass());
        jdbcMapper.insert(user);
        var obj = jdbcMapper.findById(0, user.getClass());
        System.out.println(obj.toString());
        User user1 = new User(0, "super user", 10);
        jdbcMapper.update(user1);
        var obj1 = jdbcMapper.findById(0, user.getClass());
        System.out.println(obj1.toString());

        DbExecutorImpl<Account> dbExecutor1 = new DbExecutorImpl<>();

        Account account = new Account(0, "free", 3);
        var jdbcMapper1 = new JdbcMapperImpl(dbExecutor1, sessionManager, account.getClass());


        jdbcMapper1.insert(account);
        var obj2 = jdbcMapper1.findById(0, account.getClass());
        System.out.println(obj2.toString());
        Account account1 = new Account(0, "payed", 7);
        jdbcMapper1.update(account1);
        var obj3 = jdbcMapper1.findById(0, account.getClass());
        System.out.println(obj3.toString());
        Account account2 = new Account(1, "free", 10);
        jdbcMapper1.insertOrUpdate(account2);
        var obj4 = jdbcMapper1.findById(1, account.getClass());
        System.out.println(obj4.toString());
    }

    private void createTableUser(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

    private void createTableAccount(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(50), rest int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
