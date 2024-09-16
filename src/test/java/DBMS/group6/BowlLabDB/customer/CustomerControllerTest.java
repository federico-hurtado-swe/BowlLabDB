package DBMS.group6.BowlLabDB.customer;

import java.util.List;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import DBMS.group6.BowlLabDB.customer.models.Customer;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mvc; // used to make HTTP requests during testing

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService; // create a "mock" customer service where we can define what is returned by it

    private final List<Customer> customers = new ArrayList<>();
    

    @BeforeEach
    void setUp() {
        Customer customerOne = new Customer(1, "Customer", "One", "CustomerOne@gmail.com", "111-111-1111", "PasswordOne");
        customers.add(customerOne);
    }

    @Test
    void testFindAll() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customers);

        mvc.perform(MockMvcRequestBuilders.get("/api/customer/find/all"))  
            .andExpect(status().isOk())  // Expect status 200 OK
            .andExpect(jsonPath("$", hasSize(1)))  // Ensure there is 1 customer in the list
            .andExpect(jsonPath("$[0].firstName").value("Customer"))  // Check the first name of the first customer
            .andExpect(jsonPath("$[0].email").value("CustomerOne@gmail.com"));  // Check the email of the first customer
    }
}
