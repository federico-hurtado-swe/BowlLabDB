package DBMS.group6.BowlLabDB.employee;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import DBMS.group6.BowlLabDB.employee.models.EmployeeLoginCredentials;
import DBMS.group6.BowlLabDB.employee.models.Employee;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // /find/all
    @GetMapping("/find/all")
    List<Employee> findAll() {
        return this.employeeService.getAllEmployees();
    }

    // /find/{id}
    @GetMapping("/find/{id}")
    Employee find(@PathVariable Integer id) {
        return this.employeeService.getEmployee(id);
    }

    // /create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void create(@Valid @RequestBody Employee employee) {
        this.employeeService.registerEmployee(employee);
    }

    // /update/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{id}")
    void update(@Valid @RequestBody Employee employee, @PathVariable Integer id) {
        employeeService.updateEmployee(employee, id); 
    }

    // /delete/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        employeeService.deleteEmployee(id); 
    }
    
    // /login
    /*
     * Customer log in endpoint.
     */
    @PostMapping("/login")
    ResponseEntity<String> logIn(@Valid @RequestBody EmployeeLoginCredentials credentials) {
        boolean success = employeeService.logIn(credentials.email(), credentials.password());

        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }
}
