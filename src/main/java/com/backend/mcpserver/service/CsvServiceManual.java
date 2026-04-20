package com.backend.mcpserver.service;

import com.backend.mcpserver.model.Employee;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvServiceManual {

    public List<Employee> parseCsv() {
        List<Employee> employees = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(new File("/folder/csv")))){
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts  = line.split(",");
                Employee emp = new Employee(Long.parseLong(parts[0]),parts[1], parts[2], Double.parseDouble(parts[3]),parts[4]);
                employees.add(emp);
            }
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
