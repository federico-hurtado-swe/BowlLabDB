package DBMS.group6.BowlLabDB.employee.models;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Employee {

    @Id
    private Integer id;

    @NotEmpty
    private String firstName; // cannot be empty

    @NotEmpty
    private String lastName; // cannot be empty

    @Email
    @NotEmpty
    private String email; // must be a valid email and not empty

    @NotEmpty
    private String phone; // cannot be empty, add regex if needed

    @NotEmpty
    private String addr; // cannot be empty

    @NotEmpty
    private String passkey; // hashed password

    // Constructor
    public Employee(Integer id, String firstName, String lastName, String email, String phone, String addr, String passkey) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.addr = addr;
        this.passkey = passkey;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }
}