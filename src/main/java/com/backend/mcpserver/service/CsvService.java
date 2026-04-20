package com.backend.mcpserver.service;

import com.backend.mcpserver.model.Employee;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CsvService {

    public List<Employee> loadEmployees() {
        try {
            ClassPathResource resource = new ClassPathResource("data/employees.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());

            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return  csvToBean.parse();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load CSV: " + e.getMessage());
        }
    }


}
