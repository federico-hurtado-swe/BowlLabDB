package DBMS.group6.BowlLabDB.exceptions;

/*
 * Custom exception to throw when a customer tries to register
 * with an email that is already being used.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}