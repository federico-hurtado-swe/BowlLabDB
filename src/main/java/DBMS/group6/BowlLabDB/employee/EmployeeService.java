package DBMS.group6.BowlLabDB.employee;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import DBMS.group6.BowlLabDB.employee.employeeExceptions.EmployeeNotFoundException;
import DBMS.group6.BowlLabDB.exceptions.EmailAlreadyExistsException;
import DBMS.group6.BowlLabDB.exceptions.InvalidPasswordException;
import DBMS.group6.BowlLabDB.employee.models.Employee;

/*
 * This is where business logic for the employee class will be 
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository; // Where employee data is held
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /*
     * Register a new employee.
     */
    public void registerEmployee(Employee employee) {
        // Make sure email is not already in the database
        if (this.employeeRepository.emailExistsInDB(employee.getEmail())) {
        throw new EmailAlreadyExistsException("Email is already in use.");
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(employee.getPasskey());
        employee.setPasskey(hashedPassword);

        // Add the employee to the database
        this.employeeRepository.create(employee);
    }

    /*
     * Log in an employee.
     */
    public Employee logIn(String email, String password) throws EmployeeNotFoundException, InvalidPasswordException {
        // Find employee by email
        Employee employee = this.employeeRepository.findEmployeeByEmail(email);
        
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with email does not exist.");
        }

        // Use BCryptPasswordEncoder to check if the password matches the hashed password
        if (!passwordEncoder.matches(password, employee.getPasskey())) {
            throw new InvalidPasswordException("The provided password is incorrect."); // Throw exception for wrong password
        }

        return employee; // Return employee if password is correct
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
        Employee employee = employeeRepository.getEmployeeById(id);
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
            employeeRepository.deleteEmployee((long) id); // Assuming employee ID is long
        } else {
            throw new EmployeeNotFoundException("Cannot delete. Employee with ID " + id + " not found.");
        }
    }

    /*
     * Update an existing employee.
     */
    public void updateEmployee(Employee employee, int id) throws EmployeeNotFoundException {
        // Retrieve the existing employee from the database
        Employee existingEmployee = getEmployee(id);
        if (existingEmployee == null) {
            throw new EmployeeNotFoundException("Cannot update. Employee with ID " + id + " not found.");
        }
    
        // Update only the fields that have changed
        if (employee.getEmail() != null && !employee.getEmail().equals(existingEmployee.getEmail())) {
            existingEmployee.setEmail(employee.getEmail());
        }
        if (employee.getFirstName() != null && !employee.getFirstName().equals(existingEmployee.getFirstName())) {
            existingEmployee.setFirstName(employee.getFirstName());
        }
        if (employee.getLastName() != null && !employee.getLastName().equals(existingEmployee.getLastName())) {
            existingEmployee.setLastName(employee.getLastName());
        }
        if (employee.getPhone() != null && !employee.getPhone().equals(existingEmployee.getPhone())) {
            existingEmployee.setPhone(employee.getPhone());
        }
        if (employee.getAddr() != null && !employee.getAddr().equals(existingEmployee.getAddr())) {
            existingEmployee.setAddr(employee.getAddr());
        }
    
        // Hash the password if it has been updated
        if (employee.getPasskey() != null && !passwordEncoder.matches(employee.getPasskey(), existingEmployee.getPasskey())) {
            String hashedPassword = passwordEncoder.encode(employee.getPasskey());
            existingEmployee.setPasskey(hashedPassword);
        }
    
        // Save the updated employee details
        employeeRepository.updateEmployee(existingEmployee);
    }
}