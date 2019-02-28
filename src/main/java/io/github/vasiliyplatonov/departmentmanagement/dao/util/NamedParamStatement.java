package io.github.vasiliyplatonov.departmentmanagement.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamedParamStatement {
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<>();

    public NamedParamStatement(Connection conn, String statementWithNames) throws SQLException {
        Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')");
        Matcher matcher = findParametersPattern.matcher(statementWithNames);

        while (matcher.find()) {
            fields.add(matcher.group().substring(1));
        }
        prepStmt = conn.prepareStatement(statementWithNames.replaceAll(findParametersPattern.pattern(), "?"));
    }

    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }

    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }

    public void close() throws SQLException {
        prepStmt.close();
    }

    public void setInt(String name, int value) throws SQLException {
        prepStmt.setInt(getIndex(name), value);
    }

    private int getIndex(String name) {
        return fields.indexOf(name) + 1;
    }

}
