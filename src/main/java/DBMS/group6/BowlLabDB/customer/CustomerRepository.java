package DBMS.group6.BowlLabDB.customer;

import java.util.List;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import DBMS.group6.BowlLabDB.customer.customerExceptions.CustomerNotFoundException;
import DBMS.group6.BowlLabDB.customer.models.Customer;

@Repository
public class CustomerRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /*
     * Return list of all customers
     */
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM Customers", (rs, rowNum) ->
            new Customer(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("passkey"),
                List.of((Integer[]) rs.getArray("previous_orders").getArray()),  // Cast SQL Array to List<Integer>
                rs.getInt("rewards_points")
            )
        );
    }

    /*
     * Create a new customer
     */
    public void create(Customer customer) {

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Customers(id, firstName, lastName, email, phone, passkey, previous_orders, rewards_points) values (?,?,?,?,?,?,?,?)"
            );

            ps.setInt(1, customer.id());
            ps.setString(2, customer.firstName());
            ps.setString(3, customer.lastName());
            ps.setString(4, customer.email().toLowerCase());
            ps.setString(5, customer.phone());
            ps.setString(6, customer.passkey());

            // Convert List<Integer> to PostgreSQL array
            Array previousOrdersArray = connection.createArrayOf("INTEGER", customer.previousOrders().toArray());
            ps.setArray(7, previousOrdersArray);
            
            ps.setInt(8, customer.rewardsPoints());
            return ps;

        });

        Assert.state(true, "Failed to create customer");
    }

    /*
     * Return true if a customer w/ a given email exists
     */
    public boolean emailExistsInDB(String email) {
    
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM Customers WHERE email = ?", 
            Integer.class, 
            email.toLowerCase()
        );
        
        return count != null && count > 0;
    }

    /*
     * Find customer in the DB by using their email.
     */
    @SuppressWarnings("deprecation") // query for object is depreciated but I can't figure out the new version
    public Customer findCustomerByEmail(String email) {

        String sql = "SELECT * FROM Customers WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email.toLowerCase()}, (rs, rowNum) -> 
                new Customer(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("passkey"),
                    List.of((Integer[]) rs.getArray("previous_orders").getArray()),  // Cast SQL Array to List<Integer>
                    rs.getInt("rewards_points")
                )
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // customer not found.
        }
    }


}
