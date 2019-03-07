package io.github.vasiliyplatonov.departmentmanagement.controller;

import io.github.vasiliyplatonov.departmentmanagement.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteEmployee", urlPatterns = {"/delete-employee"})
public class DeleteEmployee extends HttpServlet {

    private EmployeeService service = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteEmployee(Integer.parseInt(req.getParameter("id")));
    }
}
