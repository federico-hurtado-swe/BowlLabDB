package DBMS.group6.BowlLabDB.menu.models;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

/*
 * Record describing an item on the menu. These are all the fields
 * that are included in a MenuItem.
 */
public record MenuItem(
    @Id
    Integer id, 
    @NotEmpty // this makes sure the name is not empty
    String name,
    List<String> ingredients,
    @NotEmpty
    String description,
    @Positive // this ensures the price is a positive number
    double price
) {

}
