package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.core.dao.UserDao;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;


public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);
    private EntitySQLMetaData entitySQLMetaData;
    private EntityClassMetaData entityClassMetaData;

    @Override
    public void insert(T objectData) {

    }

    @Override
    public void update(T objectData) {

    }

    @Override
    public void insertOrUpdate(T objectData) {

    }

    @Override
    public Object findById(long id, Class clazz) {
        return null;
    }
}
