package DBMS.group6.BowlLabDB.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import DBMS.group6.BowlLabDB.customer.models.Customer;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CustomerRepository.class)
public class CustomerRepositoryTest {
    
    @Autowired
    CustomerRepository customerRepository;

    private Customer customerOne;

    @BeforeEach
    void setUp() {
        customerOne = new Customer(1, "Customer", "One", "CustomerOne@gmail.com", "111-111-1111", "PasswordOne");
        customerRepository.create(customerOne);
    }

    @Test
    void createCustomer() {
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void existsByEmailTest() {
        assertFalse(customerRepository.emailExistsInDB("email@gmail.com"));
        assertTrue(customerRepository.emailExistsInDB("CustomerOne@gmail.com"));
    }

    @Test
    void findCustomerByEmailTest() {
        Customer customer = customerRepository.findCustomerByEmail("CustomerOne@gmail.com");
        assertNotNull(customer);

        // make sure caps dont matter
        customer = customerRepository.findCustomerByEmail("CUSTOMEROne@gmail.com");
        assertNotNull(customer);

        assertEquals("Customer", customer.firstName());
        assertEquals("One", customer.lastName());
        assertEquals("111-111-1111", customer.phone());
        
        // return null if customer doesn't exist
        customer = customerRepository.findCustomerByEmail("newemail@gmail.com");
        assertNull(customer);
    }
}
