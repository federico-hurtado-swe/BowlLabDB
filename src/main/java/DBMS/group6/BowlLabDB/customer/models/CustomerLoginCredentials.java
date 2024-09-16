package DBMS.group6.BowlLabDB.customer.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/*
 * Class representing the body of customer log in credentials
 */
public record CustomerLoginCredentials(
    @NotEmpty
    @Email
    String email,
    @NotEmpty
    String password
) {

}
