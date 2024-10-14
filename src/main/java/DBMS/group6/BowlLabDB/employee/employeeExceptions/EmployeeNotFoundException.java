package DBMS.group6.BowlLabDB.employee.employeeExceptions;

public class EmployeeNotFoundException extends RuntimeException{

  public EmployeeNotFoundException(String message) {
    super(message);
}
}
