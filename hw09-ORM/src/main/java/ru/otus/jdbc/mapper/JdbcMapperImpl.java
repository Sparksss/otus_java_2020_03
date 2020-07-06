package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private EntitySQLMetaData entitySQLMetaData;
    private EntityClassMetaData<T> entityClassMetaData;
    private DbExecutor<T> dbExecutor;
    private SessionManagerJdbc sessionManager;
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    public JdbcMapperImpl(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManager, Class clazz) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClassMetaData = new EntityClassMetaDataImpl<T>(clazz);
        this.entitySQLMetaData = new EntitySQLMetaDataImpl(this.entityClassMetaData);
    }

    @Override
    public void insert(T objectData) throws SQLException {
        List<Object> values = new ArrayList<>();
        try {
            for(Field f : this.entityClassMetaData.getAllFields()) {
                values.add(f.get(objectData));
            }

            this.sessionManager.beginSession();

            this.dbExecutor.executeInsert(this.sessionManager.getCurrentSession().getConnection(), this.entitySQLMetaData.getInsertSql(), values);

            this.sessionManager.commitSession();

        } catch (IllegalAccessException e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public void update(T objectData) throws SQLException {
        List<Object> values = new ArrayList<>();
        try {
            for(Field f : this.entityClassMetaData.getFieldsWithoutId()) {
                values.add(f.get(objectData));
            }

            Field f = this.entityClassMetaData.getIdField();
            if(f == null) throw new NoSuchFieldIdException(new Exception("No such field id exception"));
            long id = Long.parseLong(f.get(objectData).toString());

            values.add(id);

            this.sessionManager.beginSession();

            this.dbExecutor.executeInsert(this.sessionManager.getCurrentSession().getConnection(), this.entitySQLMetaData.getUpdateSql(), values);

            this.sessionManager.commitSession();

        } catch (IllegalAccessException e) {
            this.logger.error(e.getMessage());
        } catch (NoSuchFieldIdException e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        try {
            Field f = this.entityClassMetaData.getIdField();
            if(f == null) throw new NoSuchFieldIdException(new Exception("No such field id exception"));
            Object val = f.get(objectData);
            if(val == null) {
                this.insert(objectData);
            } else {
                long id = Long.parseLong(val.toString());
                Object obj = this.findById(id, objectData.getClass());
                if(obj == null) {
                    this.insert(objectData);
                } else {
                    this.update(objectData);
                }
            }

        } catch (NoSuchFieldIdException | SQLException e) {
            this.logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public T findById(long id, Class clazz) {
        final Constructor<T> constructor = this.entityClassMetaData.getConstructor();
        Optional<T> extractedObj;
        T object;
        try {
            this.sessionManager.beginSession();
            extractedObj = this.dbExecutor.executeSelect(this.sessionManager.getCurrentSession().getConnection(), this.entitySQLMetaData.getSelectByIdSql(), id, rs -> {
                        try {
                            if (rs.next()) {
                                T obj = constructor.newInstance();
                                List<Field> fields = this.entityClassMetaData.getAllFields();
                                for (Field f : fields) {
                                    f.setAccessible(true);
                                    f.set(obj, rs.getObject(f.getName()));
                                }
                                return obj;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                        return null;
                    });
            this.sessionManager.commitSession();
            object = extractedObj.orElse(null);
            if (object == null) {
                this.logger.debug("Object with id was not found", id);
            }
            return object;
        } catch (SQLException e) {
            this.logger.error("Bad SQL Query: ", e);
        }
        return null;
    }
}
