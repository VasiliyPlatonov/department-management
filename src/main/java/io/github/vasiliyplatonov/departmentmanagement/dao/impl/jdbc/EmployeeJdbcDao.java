package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;

import io.github.vasiliyplatonov.departmentmanagement.domain.Employee;

public class EmployeeJdbcDao extends GenericJdbcDao<Employee> {
    /**
     * SQL queries for employees table
     * Warning! The names of columns in sql statements must be surrounded  backticks (revers apostrophe)
     *
     * Example:  INSERT INTO table_name ( `first_column` , `second_column` ) VALUES...
     *
     */
    private static final String SELECT_BY_ID =
            "SELECT e.id, e.first_name, e.last_name, e.department_id  FROM employees as e WHERE e.id = ?";

    private static final String SELECT_ALL =
            "SELECT e.id, e.first_name, e.last_name, e.department_id  FROM employees as e";

    private static final String INSERT =
            "INSERT INTO employees (`first_name` , `last_name` , `department_id`) VALUES (?,?,?)";

    private static final String UPDATE =
            "UPDATE employees SET `first_name` = ?, `last_name` = ?, `department_id` = ? WHERE `id` = ?";

    private static final String DELETE =
            "DELETE FROM employees WHERE id = ?";



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
