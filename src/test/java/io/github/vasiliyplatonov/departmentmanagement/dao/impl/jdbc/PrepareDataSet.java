package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;

import io.github.vasiliyplatonov.departmentmanagement.dao.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDataSet {
    // TODO: add a logger with information about executing

    public static void setUpDepartmentsDataSet() throws SQLException {
        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("DROP TABLE department_management_test.departments");

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

    public static void setUpEmployeesDataSet() throws SQLException {

        try (Connection connection = JdbcUtil.getConnection();
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("DROP TABLE IF EXISTS department_management_test.employees");

            statement.executeUpdate("CREATE TABLE department_management_test.employees (\n" +
                    "id INT NOT NULL AUTO_INCREMENT,\n" +
                    "first_name VARCHAR(45) NULL,\n" +
                    "last_name VARCHAR(45) NULL,\n" +
                    "department_id INT NOT NULL,\n" +
                    "PRIMARY KEY (id),\n" +
                    "CONSTRAINT employees_departments_fk\n" +
                    "FOREIGN KEY (department_id)\n" +
                    "REFERENCES department_management.departments(id)\n" +
                    "ON DELETE NO ACTION\n" +
                    "ON UPDATE NO ACTION)");

            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('John', 'Doe', '1');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Joseph', 'Eiwuley', '2');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Martin', 'Ivanov', '1');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Ivan', 'Kilich', '3');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Jan', 'Novak', '4');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Piet', 'Pietersen', '5');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Joe', 'Bloggs', '6');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Allan', 'Simon', '6');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Alex', 'Bedard', '4');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Yaniv ', 'Eidelstein', '3');");
            statement.execute("INSERT INTO `department_management_test`.`employees` (`first_name`, `last_name`, `department_id`) VALUES ('Daniel', 'Kovacs', '3');");
        }
    }
}
