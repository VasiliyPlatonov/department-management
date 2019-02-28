package io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc;

import io.github.vasiliyplatonov.departmentmanagement.dao.util.JdbcUtil;
import io.github.vasiliyplatonov.departmentmanagement.domain.Department;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class DepartmentJdbcDaoTest {
    private final DepartmentJdbcDao dao = new DepartmentJdbcDao();

    @Before
    public void setUp() throws Exception {
        PrepareDataSet.setUpDepartmentsDataSet();
    }

    @Test
    public void getEntityInstance() throws Exception {
        Object entityInstance = dao.getEntityInstance();
        assertThat(entityInstance)
                .isNotNull()
                .isInstanceOf(Department.class);
    }

    @Test
    public void findOne() {
        int id = 1;
        String name = "marketing";
        int parentId = 0;

        Department department = dao.findOne(id);

        assertThat(department).isNotNull();
        assertThat(department.getId()).isEqualTo(id);
        assertThat(department.getName()).isEqualTo(name);
        assertThat(department.getParentId()).isEqualTo(parentId);
    }

    @Test
    public void findAll() {
        List<Department> departments = dao.findAll();

        List<Integer> departmentIds = departments.stream().map(Department::getId).collect(Collectors.toList());

        //  positive test
        assertThat(departmentIds)
                .hasSize(6)
                .containsExactly(1, 2, 3, 4, 5, 6);

        // negative test
        assertThat(departmentIds).doesNotContain(10000, 0, 12);
    }

    @Test
    public void delete() {
        dao.delete(3);
        dao.delete(2);

        List<Department> departments = dao.findAll();
        List<Integer> departmentIds = departments.stream().map(Department::getId).collect(Collectors.toList());

        //  positive test
        assertThat(departmentIds)
                .hasSize(4)
                .containsExactly(1, 4, 5, 6);

        // negative test
        assertThat(departmentIds).doesNotContain(10000, 2, 3);
    }

    @Test
    public void exists() {
        boolean shouldTrue = dao.exists(1);
        boolean shouldFalse = dao.exists(10000);

        assertThat(shouldFalse).isFalse();
        assertThat(shouldTrue).isTrue();
    }

    @Test
    public void update() {
        /*
        * checking design department: {
        *   id :        5,
        *   name :      design
        *   parentId :  3
        * }
        * */

        // original state
        int originalId = 5;
        String originalName = "design";
        Integer originalParentId = 3;
        // changed state
        String changedName = "design department";
        Integer changedParentId = null;

        Department department = dao.findOne(originalId);

        // check
        assertThat(department.getId()).isEqualTo(originalId);
        assertThat(department.getName()).isEqualTo(originalName);
        assertThat(department.getParentId()).isEqualTo(originalParentId);
        // change state
        department.setName(changedName);
        department.setParentId(changedParentId);

        dao.update(department);

        // check changed state
        assertThat(department.getId()).isEqualTo(originalId);  //same id
        assertThat(department.getName()).isEqualTo(changedName);
        assertThat(department.getParentId()).isEqualTo(changedParentId);
    }

    @Test
    public void save() {
        String name = "transport";
        int parentId = 2;
        Department entity = new Department(name, parentId);

        Department department = dao.save(entity);

        assertThat(department).isNotNull();

        assertThat(department.getId())
                .isNotNull()
                .isNotEqualTo(0)
                .isNotNegative();

        assertThat(department.getParentId())
                .isNotNull()
                .isEqualTo(parentId);

        assertThat(department.getName())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(name);
    }
}