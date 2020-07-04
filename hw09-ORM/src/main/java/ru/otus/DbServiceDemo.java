package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

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

        demo.createTable(dataSource);

        User user = new User(0, "dbServiceUser", 0);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();

        var jdbcMapper = new JdbcMapperImpl(dbExecutor, sessionManager, user.getClass());
        jdbcMapper.insert(user);
        var id = jdbcMapper.findById(0, user.getClass());
        System.out.println(id.toString());

//        var dbServiceUser = new DbServiceUserImpl(userDao);
//        var id = dbServiceUser.saveUser();
//        Optional<User> user = dbServiceUser.getUser(id);
//
//        user.ifPresentOrElse(
//                crUser -> logger.info("created user, name:{}", crUser.getName()),
//                () -> logger.info("user was not created")
//        );

    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
