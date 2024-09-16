package DBMS.group6.BowlLabDB.customer;
import java.util.List;

import org.springframework.stereotype.Service;

import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.customer.customerExceptions.EmailAlreadyExistsException;
import DBMS.group6.BowlLabDB.customer.models.Customer;


/*
 * This is the class where all the logic for handling 
 * Customers in the database will be contained. 
 */
@Service
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*
     * Register a new customer. Before creating the customer, make sure
     * that the email is not already being used in the DB.
     */
    public void registerCustomer(Customer customer) throws EmailAlreadyExistsException{

        // make sure email is not already in the database
        if (this.customerRepository.emailExistsInDB(customer.email())) {
            throw new EmailAlreadyExistsException("Email is already in use.");
        }

        // add the customer to database 
        this.customerRepository.create(customer);
    }

    /*
     * Log in a user.
     */
    public boolean logIn(String email, String password) throws CustomerNotFoundException {
        
        // find user that has the given email
        Customer customer = this.customerRepository.findCustomerByEmail(email);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with email does not exist.");
        }

        // make sure the passwords match
        return (customer.passkey().equals(password));
    }

    /*
     * Return a list of all the customers stored in the DB.
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Integer id) {
        return null;
    }


    public void deleteCustomer() {
    }

    public void updateCustomer(Customer customer, Integer id) {

    } 


}
