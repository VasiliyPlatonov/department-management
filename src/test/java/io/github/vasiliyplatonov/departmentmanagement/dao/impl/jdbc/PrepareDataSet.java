package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;

import io.github.vasiliyplatonov.departmentmanagement.dao.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDataSet {

    public static void setUpDepartmentsDataSet() throws SQLException {
        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.execute("DROP TABLE department_management_test.departments");

            statement.executeUpdate("CREATE TABLE department_management_test.departments (\n" +
                    "id INT NOT NULL AUTO_INCREMENT,\n" +
                    "name VARCHAR(45) NOT NULL,\n" +
                    "parent_id INT NULL,\n" +
                    "PRIMARY KEY (id));");

            statement.execute("INSERT INTO department_management_test.departments (NAME)            VALUES ('marketing')");
            statement.execute("INSERT INTO department_management_test.departments (NAME, parent_id) VALUES ('sales', '1');");
            statement.execute("INSERT INTO department_management_test.departments (NAME)            VALUES ('development');");
            statement.execute("INSERT INTO department_management_test.departments (NAME, parent_id) VALUES ('testing', '3');");
            statement.execute("INSERT INTO department_management_test.departments (NAME, parent_id) VALUES ('design', '3');");
            statement.execute("INSERT INTO department_management_test.departments (NAME)            VALUES ('human resources');");
        }
    }
}
