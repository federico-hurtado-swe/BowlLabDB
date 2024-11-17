package DBMS.group6.BowlLabDB.employee.models;


import jakarta.validation.constraints.NotEmpty;

/*
 * Class representing the body of customer log in credentials
 */
public class EmployeeLoginCredentials {
    
    @NotEmpty(message = "Email must not be empty")
    private String email;
    
    @NotEmpty(message = "Passkey must not be empty")
    private String passkey;

    // Constructors
    public EmployeeLoginCredentials() {}

    public EmployeeLoginCredentials(String email, String passkey) {
        this.email = email;
        this.passkey = passkey;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPassword(String passkey) {
        this.passkey = passkey;
    }
}