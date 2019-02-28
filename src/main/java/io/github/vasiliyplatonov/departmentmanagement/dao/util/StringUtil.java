package io.github.vasiliyplatonov.departmentmanagement.dao.util;

public class StringUtil {

    /**
     * Method for converting from camel case to snake case.
     * Each uppercase letter will be changed to '_' and a lowercase letter
     * <pre>
     *
     *     myAwesomeString => my_awesome_string
     *
     * </pre>
     */
    public static String fromCamelCaseToSnakeCase(String str) {
        if (!hasUpperCase(str)) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(str.charAt(0)));

        str.chars().skip(1).forEach(c -> {
            if (Character.isUpperCase((char) c)) {
                result.append('_');
                c = Character.toLowerCase(c);
            }
            result.append((char) c);
        });
        return result.toString();
    }

    private static boolean hasUpperCase(String str) {
        return str.chars().anyMatch(Character::isUpperCase);
    }
}
