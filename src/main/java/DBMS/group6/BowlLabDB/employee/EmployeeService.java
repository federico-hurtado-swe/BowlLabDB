package DBMS.group6.BowlLabDB.employee;

import java.util.List;

import org.springframework.stereotype.Service;

import DBMS.group6.BowlLabDB.employee.employeeExceptions.EmployeeNotFoundException;
import DBMS.group6.BowlLabDB.employee.models.Employee;

/*
 * This is where business logic for the employee class will be 
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository; // where employee data is held

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /*
     * Register a new employee.
     */
    public void registerEmployee(Employee employee) {
        // add the employee to database
        this.employeeRepository.create(employee);
    }

    /*
     * Log in an employee.
     */
    public Employee logIn(String email, String password) throws EmployeeNotFoundException {

        // find employee by email
        Employee employee = this.employeeRepository.findEmployeeByEmail(email);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with email does not exist.");
        }

        // make sure the passwords match
        if (employee.passkey().equals(password)) {
            return employee;
        } else {
            return null;
        }

    }

    /*
     * Return a list of all the employees stored in the DB.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /*
     * Get an employee by ID.
     */
    public Employee getEmployee(int id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return employee;
    }

    /*
     * Delete an employee by ID.
     */
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        Employee employee = getEmployee(id);
        if (employee != null) {
            employeeRepository.delete((long) id); // Assuming employee ID is long
        } else {
            throw new EmployeeNotFoundException("Cannot delete. Employee with ID " + id + " not found.");
        }
    }

    /*
     * Update an existing employee.
     */
    public void updateEmployee(Employee employee, int id) throws EmployeeNotFoundException {
        Employee existingEmployee = getEmployee(id);
        if (existingEmployee != null) {
            // Update the existing employee details
            employeeRepository.update(employee);
        } else {
            throw new EmployeeNotFoundException("Cannot update. Employee with ID " + id + " not found.");
        }
    }

}
