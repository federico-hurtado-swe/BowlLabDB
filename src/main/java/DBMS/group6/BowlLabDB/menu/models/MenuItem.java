package DBMS.group6.BowlLabDB.menu.models;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/*
 * Record describing an item on the menu. These are all the fields
 * that are included in a MenuItem.
 */
public record MenuItem(
    @Id Integer id, 
    @NotEmpty String name,
    @NotEmpty String ingredients, // comma separated list in String form
    @NotEmpty String description,
    @NotNull @Positive double price,
    @Positive Integer rewardValue 
) {

}
