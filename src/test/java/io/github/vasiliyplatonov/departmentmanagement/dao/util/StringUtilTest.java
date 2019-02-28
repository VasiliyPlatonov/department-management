package io.github.vasiliyplatonov.departmentmanagement.dao.util;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StringUtilTest {

    @Test
    public void fromCamelCaseToSnakeCase() {
        String camelCase = "myAwesomeString";
        String snakeCase = "my_awesome_string";

        String result = StringUtil.fromCamelCaseToSnakeCase(camelCase);

        assertThat(result).isEqualTo(snakeCase);
    }
}

