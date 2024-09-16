package DBMS.group6.BowlLabDB.employee;

import org.springframework.stereotype.Service;

/*
 * This is where business logic for the employee class will be 
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository; // where employee data is held

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
}
