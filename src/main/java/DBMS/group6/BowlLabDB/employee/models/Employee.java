package DBMS.group6.BowlLabDB.employee.models;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public record Employee(
    @Id Integer id,
    @NotEmpty String firstName,  // cannot be empty
    @NotEmpty String lastName,   // cannot be empty
    @Email @NotEmpty String email,  // must be a valid email and not empty
    @NotEmpty String phone,      // cannot be empty, add regex if needed
    @NotEmpty String addr,    // cannot be empty
    @NotEmpty String passkey     // cannot be empty
) {}