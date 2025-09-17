package com.challenge.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import main.java.com.challenge.api.model.EmployeeCreation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.challenge.api.model.Employee;

/**
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    /*
     * New static class that I have created to serve as a Service layer.
     */
    static class EmployeeService {
        /*
         * Utilized a ConcurrentHashMap instead of HashMap as this service will be used by multiple users,
         * potentially at the same time, and map operations on a ConcurrentHashMap are thread-safe.
         */
        private static ConcurrentHashMap<UUID, Employee> employees = new ConcurrentHashMap<>();

        public EmployeeService() {
        }

        public static List<Employee> getAllEmployees() {
            return new ArrayList<>(employees.values());
        }

        public static Employee getEmployeeByUuid(UUID uuid) {
            return employees.get(uuid);
        }

        public static Employee creatEmployee(EmployeeCreation e) {
            employees.put(e.getUuid(), e);
            return e;
        }
    }
    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee models as necessary.
     * @return One or more Employees.
     */

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = EmployeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee e = EmployeeService.getEmployeeByUuid(uuid);
        if (e != null) {
            return ResponseEntity.status(HttpStatus.OK).body(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer.
     * @param requestBody hint!
     * @return Newly created Employee
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeCreation employee) {
        Employee e = EmployeeService.creatEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }
}
