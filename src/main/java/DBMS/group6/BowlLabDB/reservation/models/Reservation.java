package DBMS.group6.BowlLabDB.reservation.models;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record Reservation(
    @Id Integer id,
    @NotNull LocalDateTime reservationTime, // Combined date and time of reservation
    @NotNull Integer customerId             // References customer ID
) {}