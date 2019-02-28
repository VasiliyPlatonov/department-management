package io.github.vasiliyplatonov.departmentmanagement.dao.util;

@FunctionalInterface
public interface RowMapper<T, R> {

    T mapRow(T entity, R rowSource);
}
