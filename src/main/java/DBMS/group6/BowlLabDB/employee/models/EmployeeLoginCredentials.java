package DBMS.group6.BowlLabDB.employee.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/*
 * Class representing the body of customer log in credentials
 */
public record EmployeeLoginCredentials(
    @NotEmpty
    @Email
    String email,
    @NotEmpty
    String password
) {

}
