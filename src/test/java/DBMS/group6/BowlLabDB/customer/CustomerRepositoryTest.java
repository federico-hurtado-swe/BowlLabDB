package DBMS.group6.BowlLabDB.customer;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import DBMS.group6.BowlLabDB.customer.models.Customer;

@JdbcTest
@ActiveProfiles("test") // Use application-test.properties during tests
@Import(CustomerRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Ensure Spring doesn't replace your Testcontainers DataSource
public class CustomerRepositoryTest {

    // test container so that we do not add data to real DB during testing 
    private static PostgreSQLContainer<?> testContainer = 
            new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
    
    @Autowired
    CustomerRepository customerRepository;

    private Customer customerOne;

    static {
        testContainer.start();
    }

    @BeforeAll
    static void startContainer() {
        testContainer.start();
        System.setProperty("DB_URL", testContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", testContainer.getUsername());
        System.setProperty("DB_PASSWORD", testContainer.getPassword());
    }

    // Stop the container after all tests
    @AfterAll
    static void stopContainer() {
        testContainer.stop();
    }

    @BeforeEach
    void setUp() {
        
        customerOne = new Customer(1, "Customer", "One", "CustomerOne@gmail.com", "111-111-1111", "PasswordOne", List.of(1), 0);
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
