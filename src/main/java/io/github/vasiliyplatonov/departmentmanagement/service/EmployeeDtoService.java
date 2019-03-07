package io.github.vasiliyplatonov.departmentmanagement.service;

import io.github.vasiliyplatonov.departmentmanagement.dao.impl.jdbc.DepartmentJdbcDao;
import io.github.vasiliyplatonov.departmentmanagement.domain.Department;
import io.github.vasiliyplatonov.departmentmanagement.domain.Employee;
import io.github.vasiliyplatonov.departmentmanagement.dto.EmployeeDto;

public class EmployeeDtoService {

    private DepartmentJdbcDao departmentDao = new DepartmentJdbcDao();

    public EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName());
        Department department = departmentDao.findOne(employee.getDepartmentId());
        employeeDto.setDepartment(department);
        return employeeDto;
    }
}
