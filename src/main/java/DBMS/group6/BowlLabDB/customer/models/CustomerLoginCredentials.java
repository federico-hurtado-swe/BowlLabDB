package DBMS.group6.BowlLabDB.customer.models;

import jakarta.validation.constraints.NotEmpty;

public class CustomerLoginCredentials {
    
    @NotEmpty(message = "Email must not be empty")
    private String email;
    
    @NotEmpty(message = "Passkey must not be empty")
    private String passkey;

    // Constructors
    public CustomerLoginCredentials() {}

    public CustomerLoginCredentials(String email, String passkey) {
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