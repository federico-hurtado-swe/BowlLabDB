package DBMS.group6.BowlLabDB.menu.models;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

/*
 * Record describing an item on the menu. These are all the fields
 * that are included in a MenuItem.
 */
public record MenuItem(
        @Id Integer id,
        @NotEmpty String name,
        @NotEmpty String description,
        @Positive @NotEmpty double price,
        @Positive Integer rewardValue) {

}
