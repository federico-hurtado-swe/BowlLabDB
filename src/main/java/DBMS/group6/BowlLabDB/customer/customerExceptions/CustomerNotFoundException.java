package DBMS.group6.BowlLabDB.customer.customerExceptions;


public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}