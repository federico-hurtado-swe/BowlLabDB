package DBMS.group6.BowlLabDB.employee;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    
    private final JdbcClient jdbcClient;

    public EmployeeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // findAll

    // findByID

    // create

    // update

    // delete

    // validateLogin

    
}
