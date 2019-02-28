package io.github.vasiliyplatonov.departmentmanagement.dao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.github.vasiliyplatonov.departmentmanagement.dao.util.StringUtil.fromCamelCaseToSnakeCase;


public class StatementUtil {
    private static final Logger logger = LoggerFactory.getLogger(StatementUtil.class);
//
//    public static <T> PreparedStatement setEntityToStatement(T entity, Connection connection, String sql) throws SQLException {
//        Field[] fields = entity.getClass().getDeclaredFields();
//        String[] columns;
//        PreparedStatement statement = null;
//
//        try {
//            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//            columns = getColumnNames(sql);
//
//            for (int i = 0; i < columns.length; i++) {
//                for (Field field : fields) {
//                    String fieldName = field.getName();
//                    if (hasUpperCase(fieldName))
//                        fieldName = fromCamelCaseToSnakeCase(fieldName);
//
//                    if (fieldName.equals(columns[i])) {
//                        field.setAccessible(true);
//                        statement.setObject(i + 1, field.get(entity));
//                        field.setAccessible(false);
//                    }
//                }
//            }
//        } catch (BuildInsertStatementException | SQLException | IllegalAccessException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return statement;
//    }
//
//    private static String[] getColumnNames(String insertSql) throws BuildInsertStatementException {
////        Pattern columnsPattern = Pattern.compile("(\\(.*\\w\\))"); //with quotes:( "\\('?.*\\w'?\\)" )
//        Pattern columnsPattern = Pattern.compile(":\\w+\\b(?=(?:[^\"'\\\\]*(?:\\\\.|([\"'])(?:(?:(?!\\\\|\\1).)*\\\\.)*(?:(?!\\\\|\\1).)*\\1))*[^\"']*$)"); //with quotes:( "\\('?.*\\w'?\\)" )
//        Matcher matcher = columnsPattern.matcher(insertSql);
//
//        if (!matcher.find())
//            throw new BuildInsertStatementException("Make sure that the column names in sql insert are without the quotes");
//
//        return insertSql
//                .substring(matcher.start() + 1, matcher.end() - 1)
//                .split("(,(\\s?))");
//    }
//
//    /**
//     * Method retrieves index of parameter
//     */
//    private static int getIndexMappedEntityField(String[] statementParam, String fieldName) {
//        if (hasUpperCase(fieldName))
//            fieldName = fromCamelCaseToSnakeCase(fieldName);
//
//        for (int i = 0; i < statementParam.length; i++) {
//            if (statementParam[i].equals(fieldName))
//                return i;
//        }
//
//        return 0;
//    }


    public static <T> PreparedStatement getPreparedEntityStatement(T entity, Connection connection, String sql) {
        Field[] fields = entity.getClass().getDeclaredFields();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            List<String> orderedColNames = getOrderedColumnNames(sql, fields);
            for (Field field : fields) {
                field.setAccessible(true);
                int paramIndex = orderedColNames.indexOf(fromCamelCaseToSnakeCase(field.getName()));
                if (paramIndex >= 0) {
                    statement.setObject(
                            paramIndex + 1,
                            field.get(entity));
                    field.setAccessible(false);
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return statement;
    }


    /**
     * Method for generating a list of column names ordered by priority in sql
     *
     * @param fields - entity fields array
     * @return list of column names with mandatory priority
     */
    private static List<String> getOrderedColumnNames(String sql, Field[] fields) {
        List<String> colNames = Arrays
                .stream(fields)
                .map(Field::getName)
                .map(StringUtil::fromCamelCaseToSnakeCase)
                .collect(Collectors.toList());

        Map<Integer, String> indexesOfColName = new TreeMap<>();
        for (String name : colNames) {
//            Pattern pattern = Pattern.compile("\\s" + name + "\\s");
            Pattern pattern = Pattern.compile("`" + name + "`");
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {
                indexesOfColName.put(matcher.start(), name);
            }
        }

        return new ArrayList<>(indexesOfColName.values());
    }
}
