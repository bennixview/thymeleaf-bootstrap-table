package com.bewi.stockmanager.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bewi.stockmanager.model.Employee;
import com.bewi.stockmanager.model.paging.Page;
import com.bewi.stockmanager.model.paging.Paged;
import com.bewi.stockmanager.model.paging.Paging;

@Service
public class EmployeeService {

    public Paged<Employee> getEmployees(int pageNumber, int size) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Employee> employees = objectMapper.readValue(getClass().getClassLoader()
                            .getResourceAsStream("employees.json"),
                    new TypeReference<List<Employee>>() {
                    });

            List<Employee> paged = employees.stream()
                    .skip(pageNumber)
                    .limit(size)
                    .collect(Collectors.toList());

            int totalPages = employees.size() / size;
            return new Paged<>(new Page<>(paged, totalPages), Paging.of(totalPages, pageNumber, size));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Paged<>();
    }
}
