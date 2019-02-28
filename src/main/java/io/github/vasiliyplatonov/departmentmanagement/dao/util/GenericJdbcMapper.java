package io.github.vasiliyplatonov.departmentmanagement.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericJdbcMapper<T> implements RowMapper<T, ResultSet> {
    private static final Logger logger = LoggerFactory.getLogger(RowMapper.class);

    @Override
    public T mapRow(T entity, ResultSet resultSet) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                Object fieldData = getFieldDataFromResultSet(resultSet, field);
                field.setAccessible(true);
                field.set(entity, fieldData);
                field.setAccessible(false);
            } catch (SQLException e) {
                logger.error("Exception:  while row mapper was trying to get field`s data from resultSet", e);
            } catch (IllegalAccessException e) {
                logger.error("Exception:  while row mapper was trying to set field`s data", e);
            }
        }
        return entity;
    }

    private Object getFieldDataFromResultSet(ResultSet resultSet, Field field) throws SQLException {
        String columnName = StringUtil.fromCamelCaseToSnakeCase(field.getName());
        return resultSet.getObject(columnName, field.getType());
    }

}
