package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;


import io.github.vasiliyplatonov.departmentmanagement.domain.Department;

public class DepartmentJdbcDao extends GenericJdbcDao<Department> {

    /**
     * SQL queries for departments table
     * Warning! The names of columns in sql statements must be surrounded  backticks (revers apostrophe)
     *
     * Example:  INSERT INTO table_name ( `first_column` , `second_column` ) VALUES...
     *
     */
    private static final String SELECT_BY_ID =
            "SELECT d.id, d.name, d.parent_id FROM departments as d WHERE d.id = ?";

    private static final String SELECT_ALL =
            "SELECT d.id, d.name, d.parent_id FROM departments as d";

    private static final String INSERT =
            "INSERT INTO departments (`name` , `parent_id` ) VALUES (?,?)";

    private static final String UPDATE =
            "UPDATE departments SET `name` = ?, `parent_id` = ? WHERE `id` = ?";

    private static final String DELETE =
            "DELETE FROM departments WHERE id = ?";


    @Override
    String getInsertQuery() {
        return INSERT;
    }

    @Override
    String getUpdateQuery() {
        return UPDATE;
    }

    @Override
    String getDeleteQuery() {
        return DELETE;
    }

    @Override
    public String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    String getSelectAllQuery() {
        return SELECT_ALL;
    }
}
