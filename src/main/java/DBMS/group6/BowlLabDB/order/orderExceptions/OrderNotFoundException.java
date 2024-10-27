package DBMS.group6.BowlLabDB.order.orderExceptions;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}