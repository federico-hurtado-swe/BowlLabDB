package DBMS.group6.BowlLabDB.exceptions;

/*
 * Custom exception to handle invalid password scenarios.
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}