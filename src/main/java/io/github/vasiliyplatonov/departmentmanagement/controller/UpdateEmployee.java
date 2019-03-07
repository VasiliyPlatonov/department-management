package io.github.vasiliyplatonov.departmentmanagement.controller;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.TODO;
import io.github.vasiliyplatonov.departmentmanagement.domain.Employee;
import io.github.vasiliyplatonov.departmentmanagement.dto.EmployeeDto;
import io.github.vasiliyplatonov.departmentmanagement.service.EmployeeDtoService;
import io.github.vasiliyplatonov.departmentmanagement.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "updateEmployee", urlPatterns = {"/update-employee"})
public class UpdateEmployee extends HttpServlet {

    private Gson gson = new Gson();
    private EmployeeService service = new EmployeeService();
    private EmployeeDtoService dtoService = new EmployeeDtoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = gson.fromJson(req.getParameter("employee"), Employee.class);
        employee = service.updateEmployee(employee);

        EmployeeDto employeeDto = dtoService.mapToDto(employee);
        String employeeJsonString = gson.toJson(employeeDto);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }

    // TODO: Rewrite without dto layer. Make employee`s domain who has a department as an object and not as just id of department*
}
