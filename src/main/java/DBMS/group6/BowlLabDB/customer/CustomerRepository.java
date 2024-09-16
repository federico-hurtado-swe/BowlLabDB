package DBMS.group6.BowlLabDB.customer;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import DBMS.group6.BowlLabDB.customer.models.Customer;

@Repository
public class CustomerRepository {
    
    private final JdbcClient jdbcClient;

    public CustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    /*
     * Return list of all customers
     */
    public List<Customer> findAll() {
        return jdbcClient.sql("select * from Customers")
            .query(Customer.class)
            .list();
    }

    /*
     * Create a new customer
     */
    public void create(Customer customer) {

        var updated = jdbcClient.sql("INSERT INTO Customers(id, firstName, lastName, email, phone, passkey) values (?,?,?,?,?,?)")
            .params(List.of(customer.id(), customer.firstName(), customer.lastName(), customer.email().toLowerCase(), customer.phone(), customer.passkey()))
            .update();
            
        Assert.state(updated == 1, "Failed to create customer"); // make sure only 1 row is affected 
    }

    /*
     * Return true if a customer w/ a given email exists
     */
    public boolean emailExistsInDB(String email) {
        try {
        Integer count = jdbcClient.sql("SELECT COUNT(*) FROM Customers WHERE email = ?")
            .param(email.toLowerCase())
            .query(Integer.class)
            .single();

            // return true if a row was found
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            // no results are found
            return false;
        }
    }

    /*
     * Find customer in the DB by using their email.
     */
    public Customer findCustomerByEmail(String email) {
        try {
            Customer customer = jdbcClient.sql("SELECT * FROM Customers WHERE email = ?")
            .param(email.toLowerCase())
            .query(Customer.class)
            .single();

            return customer;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
