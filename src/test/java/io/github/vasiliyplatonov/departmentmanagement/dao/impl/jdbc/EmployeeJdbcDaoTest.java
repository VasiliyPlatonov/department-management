package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;

import io.github.vasiliyplatonov.departmentmanagement.domain.Department;
import io.github.vasiliyplatonov.departmentmanagement.domain.Employee;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeJdbcDaoTest {
    private final EmployeeJdbcDao dao = new EmployeeJdbcDao();

    @Before
    public void setUp() throws Exception {
        PrepareDataSet.setUpEmployeesDataSet();
    }

    @Test
    public void getEntityInstance() throws Exception {
        Object entityInstance = dao.getEntityInstance();
        assertThat(entityInstance)
                .isNotNull()
                .isInstanceOf(Employee.class);
    }

    @Test
    public void findOne() {
        int id = 1;
        String first_name = "John";
        String last_name = "Doe";
        int departmentId = 1;

        Employee employee = dao.findOne(1);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(id);
        assertThat(employee.getFirstName()).isEqualTo(first_name);
        assertThat(employee.getLastName()).isEqualTo(last_name);
        assertThat(employee.getDepartmentId()).isEqualTo(departmentId);
    }

    @Test
    public void findAll() {
        List<Employee> employees = dao.findAll();

        List<Integer> employeeIds = employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        //  positive test
        assertThat(employeeIds)
                .hasSize(11)
                .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

        // negative test
        assertThat(employeeIds).doesNotContain(10000, 0, 322);
    }

    @Test
    public void delete() {
        dao.delete(3);
        dao.delete(2);

        List<Employee> employees = dao.findAll();
        List<Integer> employeeIds = employees.stream().map(Employee::getId).collect(Collectors.toList());

        //  positive test
        assertThat(employeeIds)
                .hasSize(9)
                .containsExactly(1, 4, 5, 6, 7, 8, 9, 10, 11);

        // negative test
        assertThat(employeeIds).doesNotContain(10000, 2, 3);
    }

    @Test
    public void exists() {
        boolean shouldTrue = dao.exists(1);
        boolean shouldFalse = dao.exists(10000);

        assertThat(shouldFalse).isFalse();
        assertThat(shouldTrue).isTrue();
    }


    @Test
    public void save() {
        String firstName = "Vasiliy";
        String lastName = "Platonov";
        int departmentId = 3;
        Employee entity = new Employee();

        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setDepartmentId(departmentId);

        Employee employee = dao.save(entity);

        assertThat(employee).isNotNull();

        assertThat(employee.getId())
                .isNotNull()
                .isNotEqualTo(0)
                .isNotNegative();

        assertThat(employee.getDepartmentId())
                .isNotNull()
                .isEqualTo(departmentId);

        assertThat(employee.getFirstName())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(firstName);

        assertThat(employee.getLastName())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(lastName);
    }

    @Test
    public void update() {
        // original state
        int originalId = 5;
        String originalFName = "Jan";
        String originalLName = "Novak";
        Integer originalDepartmentId = 4;
        // changed state
        String changedFName = "Vasiliy";
        String changedLName = "Platonov";
        Integer changedDepartmentId = 3;

        Employee employee = dao.findOne(originalId);

        // check
        assertThat(employee.getId()).isEqualTo(originalId);
        assertThat(employee.getFirstName()).isEqualTo(originalFName);
        assertThat(employee.getLastName()).isEqualTo(originalLName);
        // change state
        employee.setFirstName(changedFName);
        employee.setLastName(changedLName);
        employee.setDepartmentId(changedDepartmentId);

        dao.update(employee);

        // check changed state
        assertThat(employee.getId()).isEqualTo(originalId);
        assertThat(employee.getFirstName()).isEqualTo(changedFName);
        assertThat(employee.getLastName()).isEqualTo(changedLName);
        assertThat(employee.getDepartmentId()).isEqualTo(changedDepartmentId);
    }
}
