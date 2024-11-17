package DBMS.group6.BowlLabDB.customer;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import DBMS.group6.BowlLabDB.exceptions.EmailAlreadyExistsException;
import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.exceptions.InvalidPasswordException;
import DBMS.group6.BowlLabDB.customer.models.Customer;

/*
 * This is the class where all the logic for handling 
 * Customers in the database will be contained. 
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*
     * Register a new customer. Before creating the customer, make sure
     * that the email is not already being used in the DB.
     */
    public void registerCustomer(Customer customer) throws EmailAlreadyExistsException {
        // Make sure email is not already in the database
        if (this.customerRepository.emailExistsInDB(customer.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already in use.");
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(customer.getPasskey());
        customer.setPasskey(hashedPassword);

        // Add the customer to the database
        this.customerRepository.create(customer);
    }

    /*
     * Log in a user.
     */
    public Customer logIn(String email, String password) throws CustomerNotFoundException, InvalidPasswordException {
        // Find user that has the given email
        Customer customer = this.customerRepository.findCustomerByEmail(email);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email does not exist.");
        }

        // Use BCryptPasswordEncoder to check if the password matches the hashed password
        if (!passwordEncoder.matches(password, customer.getPasskey())) {
            throw new InvalidPasswordException("The provided password is incorrect.");
        }

        return customer; // Passwords match, return the customer
    }

    /*
     * Return a list of all the customers stored in the DB.
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /*
     * Get an employee by ID.
     */
    public Customer getCustomer(Integer id) throws CustomerNotFoundException {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return customer;
    }

    /*
     * Delete an customer by ID.
     */
    public void deleteCustomer(Integer id) throws CustomerNotFoundException {
        Customer customer = customerRepository.getCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        customerRepository.deleteCustomer(id);
    }

     /*
     * Update an existing customer.
     */
    public Customer updateCustomer(Customer customer, Integer id) throws CustomerNotFoundException {
        // Retrieve the existing customer from the database
        Customer existingCustomer = customerRepository.getCustomerById(id);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Cannot update. Customer with ID " + id + " not found.");
        }
    
        // Update only the fields that have changed
        if (customer.getEmail() != null && !customer.getEmail().equals(existingCustomer.getEmail())) {
            existingCustomer.setEmail(customer.getEmail());
        }
        if (customer.getFirstName() != null && !customer.getFirstName().equals(existingCustomer.getFirstName())) {
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null && !customer.getLastName().equals(existingCustomer.getLastName())) {
            existingCustomer.setLastName(customer.getLastName());
        }
      
        if (customer.getPhone() != null && !customer.getPhone().equals(existingCustomer.getPhone())) {
            existingCustomer.setPhone(customer.getPhone());
        }
    
        // Hash the password if it has been updated
        if (customer.getPasskey() != null && !passwordEncoder.matches(customer.getPasskey(), existingCustomer.getPasskey())) {
            String hashedPassword = passwordEncoder.encode(customer.getPasskey());
            existingCustomer.setPasskey(hashedPassword);
        }
    
        // Save the updated customer details
        customerRepository.updateCustomer(existingCustomer, id);
        return existingCustomer;
    }
}