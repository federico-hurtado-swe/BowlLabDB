package DBMS.group6.BowlLabDB.customer.models;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record Customer(
    @Id
    Integer id,
    @NotEmpty
    String firstName, // cannot be empty
    @NotEmpty
    String lastName, // cannot be empty
    @Email
    @NotEmpty
    String email, // must be an email and not empty
    String phone,
    @NotEmpty
    String passkey // cannot be empty
) {

}