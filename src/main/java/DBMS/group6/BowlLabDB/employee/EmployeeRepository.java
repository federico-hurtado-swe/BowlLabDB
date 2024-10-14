package DBMS.group6.BowlLabDB.employee;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import DBMS.group6.BowlLabDB.customer.models.Customer;
import DBMS.group6.BowlLabDB.employee.models.Employee;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Return list of all employees
     */
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM Employee", (rs, rowNum) ->
            new Employee(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("passkey")
            )
        );
    }

    /*
     * Create a new employee
     */
    public void create(Employee employee) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Employee(first_name, last_name, email, phone, address, password) VALUES (?,?,?,?,?,?)"
            );
            ps.setString(1, employee.firstName());
            ps.setString(2, employee.lastName());
            ps.setString(3, employee.email().toLowerCase());
            ps.setString(4, employee.phone());
            ps.setString(5, employee.address());
            ps.setString(6, employee.passkey());
            return ps;
        });
    }


    /*
     * Find employee by ID
     */
    @SuppressWarnings("deprecation") 
    public Employee findById(int id) {
        String sql = "SELECT * FROM Employee WHERE employeeID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Employee(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("passkey")
                )
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // employee not found
        }
    } 
    
    /*
     * Update an existing employee
     */
    public void update(Employee employee) {
        String sql = "UPDATE Employee SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, password = ?, approved_by = ? WHERE employeeID = ?";
        jdbcTemplate.update(sql,
            employee.firstName(),
            employee.lastName(),
            employee.email().toLowerCase(),
            employee.phone(),
            employee.address(),
            employee.passkey()
          
        );
    }

    /*
     * Delete an employee by ID
     */
    public void delete(Long id) {
        String sql = "DELETE FROM Employee WHERE employeeID = ?";
        jdbcTemplate.update(sql, id);
    }

    /*
     * Validate login credentials (email and password)
     */
    @SuppressWarnings("deprecation") 
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT COUNT(*) FROM Employee WHERE email = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email.toLowerCase(), password}, Integer.class);
        return count != null && count > 0;
    }

    /*
    * Find Employee in the DB by using their email.
    */
    @SuppressWarnings("deprecation")
    public Employee findEmployeeByEmail(String email) {
        String sql = "SELECT * FROM Employee WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email.toLowerCase()}, (rs, rowNum) ->
                new Employee(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("passkey")
                )
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // employee not found
        }
    }
}