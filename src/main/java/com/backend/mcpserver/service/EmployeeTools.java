package com.backend.mcpserver.service;

import com.backend.mcpserver.model.Employee;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeTools {

    @Autowired
    private CsvService csvService;

    @Autowired
    private LlmService llmService;


    @Tool(description = "Get all employees from the company CSV file")
    public List<Employee> getAllEmployees() {
        return csvService.loadEmployees();
    }

    @Tool(description = "Get employees filtered by department name")
    public List<Employee> getEmployeesByDepartment(String department) {
        return csvService.loadEmployees()
                .stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    @Tool(description = "Get employees by location")
    public List<Employee> getEmployeesByLocation(String location) {
        return csvService.loadEmployees()
                .stream()
                .filter(e -> e.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    @Tool(description = "Get total salary budget for a department")
    public double getDepartmentSalaryBudget(String department) {
        return csvService.loadEmployees()
                .stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .sum();
    }


    public String ask(String question) {
        String context = buildEmployeeContext();
        return llmService.ask(question, context);
    }


    private String buildEmployeeContext() {
        List<Employee> employees = csvService.loadEmployees();
        return employees.stream()
                .map(e -> e.getName() + " | " + e.getDepartment()
                        + " | $" + e.getSalary() + " | " + e.getLocation())
                .collect(Collectors.joining("\n"));
    }
}