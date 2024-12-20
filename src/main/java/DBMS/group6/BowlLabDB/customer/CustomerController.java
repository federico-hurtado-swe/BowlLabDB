package DBMS.group6.BowlLabDB.customer;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import DBMS.group6.BowlLabDB.exceptions.EmailAlreadyExistsException;
import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.exceptions.InvalidPasswordException;
import DBMS.group6.BowlLabDB.customer.models.Customer;
import DBMS.group6.BowlLabDB.customer.models.CustomerLoginCredentials;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/find/all")
    List<Customer> findAll() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("/find/{id}")
    Customer find(@PathVariable Integer id) {
        return customerService.getCustomer(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void create(@Valid @RequestBody Customer customer) {
        this.customerService.registerCustomer(customer);
    }

    @PutMapping("/update/{id}")
    Customer update(@Valid @RequestBody Customer customer, @PathVariable Integer id) {
        return customerService.updateCustomer(customer, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        this.customerService.deleteCustomer(id);
    }

    /*
     * Customer log in endpoint.
     */
    @PostMapping("/login")
    ResponseEntity<Customer> logIn(@Valid @RequestBody CustomerLoginCredentials credentials) {
        try {
            Customer customer = customerService.logIn(credentials.getEmail(), credentials.getPasskey());
            return ResponseEntity.ok(customer); // Return 200 OK with the customer details
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Return 401 Unauthorized
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found
        }
    }

    // --------- Exception Handling --------------

    // Handle EmailAlreadyExistsException with a 409 code always
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public String handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }

    // Handle CustomerNotFoundException and return a 404 Not Found response
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomerNotFound(CustomerNotFoundException ex) {
        return ex.getMessage();
    }

    // Handle InvalidPasswordException and return a 401 Unauthorized response
    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidPassword(InvalidPasswordException ex) {
        return ex.getMessage();
    }

}