package DBMS.group6.BowlLabDB.reviews.models;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;

public record Review(
    @Id Integer id,
    @NotNull Integer written_by,
    int stars_given,
    String description

) {}
