package DBMS.group6.BowlLabDB.customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.customer.customerExceptions.EmailAlreadyExistsException;
import DBMS.group6.BowlLabDB.customer.models.Customer;

@SpringBootTest
public class CustomerServiceTest {
    
    @MockBean 
    CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    private Customer customerOne;

    @BeforeEach
    void setUp() {
        customerOne = new Customer(1, "Customer", "One", "CustomerOne@gmail.com", "111-111-1111", "PasswordOne");
     
    }


    @Test
    void registerCustomerTest() {

        // case 1: email does not exist
        when(customerRepository.emailExistsInDB(customerOne.email())).thenReturn(false);
        doNothing().when(customerRepository).create(Mockito.any(Customer.class));
        customerService.registerCustomer(customerOne);
        Mockito.verify(customerRepository, Mockito.times(1)).create(customerOne);

        // case 2: email already exists in db
        when(customerRepository.emailExistsInDB(customerOne.email())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> {
            customerService.registerCustomer(customerOne); // make sure the service throws the exception
        });

    }

    @Test
    void loginTest() {

        // case 1: the email used to log in exists in the database
        when(customerRepository.findCustomerByEmail(customerOne.email())).thenReturn(customerOne);
        assertTrue(customerService.logIn(customerOne.email(), customerOne.passkey())); // correct password 
        assertFalse(customerService.logIn(customerOne.email(), customerOne.passkey().toUpperCase())); // incorrect password

        // case 2: the email used to log in does not exist in the database
        when(customerRepository.findCustomerByEmail(customerOne.email())).thenReturn(null); // customerOne does not exist
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.logIn(customerOne.email(), customerOne.passkey()); // make sure the service throws the exception
        });

    }



    

}
