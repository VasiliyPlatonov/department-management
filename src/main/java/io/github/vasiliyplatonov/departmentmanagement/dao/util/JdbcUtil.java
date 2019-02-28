package io.github.vasiliyplatonov.departmentmanagement.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class JdbcUtil {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    private static ResourceBundle resource = ResourceBundle.getBundle("database");
    private static final String URL = resource.getString("db.url");
    private static final String USERNAME = resource.getString("db.username");
    private static final String PASSWORD = resource.getString("db.password");

    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("Occurred SQLException while trying to get a connection", e);
        }
        return connection;
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("Could not close JDBC ResultSet", ex);
            } catch (Throwable ex) {
                // We don't trust the JDBC driver: It might throw
                // RuntimeException or Error.
                logger.error("Unexpected exception on closing JDBC ResultSet", ex);
            }
        }
    }
}
