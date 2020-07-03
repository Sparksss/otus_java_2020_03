package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + this.entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + this.entityClassMetaData.getName() + " WHERE id = (?)";
    }

    @Override
    public String getInsertSql() {
        StringBuilder query = new StringBuilder("INSERT INTO " + this.entityClassMetaData.getName() + " (");
        List<Field> fields = this.entityClassMetaData.getFieldsWithoutId();
        int countFields = fields.size();
        for(int i = 0; i < countFields; i++) {
            query.append(fields.get(i).getName());
            if(!((i + 1) == countFields)) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");
        query.append("? ".repeat(this.entityClassMetaData.getAllFields().size()));
        query.append(")");
        return  query.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder query = new StringBuilder("UPDATE " + this.entityClassMetaData.getName() + " SET ");
        List<Field> fields = this.entityClassMetaData.getFieldsWithoutId();
        int countFields = fields.size();
        for(int i = 0; i < countFields; i++) {
            query.append(fields.get(i).getName() + " = ?");
            if(!((i + 1) == countFields)) {
                query.append(", ");
            }
        }
        query.append(" WHERE id = (?)");
        return query.toString();
    }
}
