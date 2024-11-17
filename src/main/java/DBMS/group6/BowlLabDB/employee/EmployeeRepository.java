package DBMS.group6.BowlLabDB.employee;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.query("SELECT * FROM Employees", (rs, rowNum) -> new Employee(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("addr"),
                rs.getString("passkey")));
    }

    /*
     * Create a new employee
     */
    public void create(Employee employee) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Employees(firstName, lastName, email, phone, addr, passkey) VALUES (?,?,?,?,?,?)");
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail().toLowerCase());
            ps.setString(4, employee.getPhone());
            ps.setString(5, employee.getAddr());
            ps.setString(6, employee.getPasskey());
            return ps;
        });
    }

    /*
     * Return true if a employee w/ a given email exists
     */
    public boolean emailExistsInDB(String email) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Employees WHERE email = ?",
                Integer.class,
                email.toLowerCase());

        return count != null && count > 0;
    }


    /*
     * Find Employee in the DB by using their email.
     */
    @SuppressWarnings("deprecation")
    public Employee findEmployeeByEmail(String email) {
        String sql = "SELECT * FROM Employees WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[] { email.toLowerCase() }, (rs, rowNum) -> new Employee(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("addr"),
                    rs.getString("passkey")));
        } catch (EmptyResultDataAccessException e) {
            return null; // employee not found
        }
    }

    /*
     * Find employee by ID
     */
    @SuppressWarnings("deprecation")
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM Employees WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> new Employee(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("addr"),
                    rs.getString("passkey")));
        } catch (EmptyResultDataAccessException e) {
            return null; // employee not found
        }
    }

    /*
     * Update an existing employee
     */
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employees SET firstName = ?, lastName = ?, email = ?, phone = ?, addr = ?, passkey = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail().toLowerCase(),
                employee.getPhone(),
                employee.getAddr(),
                employee.getPasskey(),
                employee.getId());
    }

    /*
     * Delete an employee by ID
     */
    public void deleteEmployee(Long id) {
        String sql = "DELETE FROM Employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /*
     * Validate login credentials (email and password)
     */
    @SuppressWarnings("deprecation")
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT COUNT(*) FROM Employees WHERE email = ? AND passkey = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { email.toLowerCase(), password }, Integer.class);
        return count != null && count > 0;
    }


}