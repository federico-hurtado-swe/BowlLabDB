package DBMS.group6.BowlLabDB.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // /find/all

    // /find/{id}

    // /create

    // /update/{id}

    // /delete/{id}
    
    // /login
}
