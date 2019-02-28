package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;


import io.github.vasiliyplatonov.departmentmanagement.dao.GenericDao;
import io.github.vasiliyplatonov.departmentmanagement.dao.util.GenericJdbcMapper;
import io.github.vasiliyplatonov.departmentmanagement.dao.util.JdbcUtil;
import io.github.vasiliyplatonov.departmentmanagement.dao.util.StatementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GenericJdbcDao<T> implements GenericDao<T, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(GenericJdbcDao.class);

    private GenericJdbcMapper<T> rowMapper;
    private Class<T> entityClass;

    abstract String getSelectByIdQuery();

    abstract String getSelectAllQuery();

    abstract String getInsertQuery();

    abstract String getUpdateQuery();

    abstract String getDeleteQuery();

    protected T getEntityInstance() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Exception:  trying to return entity instance", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected GenericJdbcDao() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.rowMapper = new GenericJdbcMapper();
    }

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = StatementUtil
                      .getPreparedEntityStatement(entity, connection, getInsertQuery())

        ) {
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                try {
                    Field fieldId = entity.getClass()
                            .getDeclaredField("id");
                    fieldId.setAccessible(true);
                    fieldId.set(entity, generatedKeys.getInt(1));
                    fieldId.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.error("Exception while trying to set id of entity");
                }
            }

            JdbcUtil.closeResultSet(generatedKeys);
        } catch (SQLException e) {
            logger.error("SQLException:  while trying to save  with", e);
        }

        return entity;
    }

    @Override
    public int save(List<T> entities) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public T findOne(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");

        T entity = getEntityInstance();
        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getSelectByIdQuery())
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                entity = rowMapper.mapRow(entity, resultSet);

            JdbcUtil.closeResultSet(resultSet);
        } catch (SQLException e) {
            logger.error("SQLException:  while trying to findOne with", e);
        }

        return entity;
    }

    @Override
    public boolean exists(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");

        boolean result = false;

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getSelectByIdQuery())
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();

            JdbcUtil.closeResultSet(resultSet);
        } catch (SQLException e) {
            logger.error("SQLException:  while trying to find out if an entity exists", e);
        }
        return result;

    }

    @Override
    public List<T> findAll() {
        ArrayList<T> resultList = new ArrayList<>();

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getSelectAllQuery());
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                T entity = rowMapper.mapRow(getEntityInstance(), resultSet);
                resultList.add(entity);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public int count() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");
        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getDeleteQuery())
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error("SQLException:  while trying to delete entity", e);
        }
    }

    @Override
    public void update(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");

        try (
                Connection connection = JdbcUtil.getConnection();
                PreparedStatement statement = StatementUtil.getPreparedEntityStatement(entity, connection, getUpdateQuery())
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException:  while trying to update entity", e);
        }

    }

}
