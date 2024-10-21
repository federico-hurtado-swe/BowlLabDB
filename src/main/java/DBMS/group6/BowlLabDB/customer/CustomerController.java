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

import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.customer.customerExceptions.EmailAlreadyExistsException;
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
        // TODO: implement this in customerService
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void create(@Valid @RequestBody Customer customer) {
        this.customerService.registerCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{id}")
    void update(@Valid @RequestBody Customer customer, @PathVariable Integer id) {
        // TODO: implement this in customerService
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        // TODO: implement this in customerService
    }

    /*
     * Customer log in endpoint.
     */
    @PostMapping("/login")
    Customer logIn(@Valid @RequestBody CustomerLoginCredentials credentials) {
        Customer customer = customerService.logIn(credentials.email(), credentials.password());

        if (customer != null) {
            return customer;
        } else {
            return null; // return null if customer not found
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

}