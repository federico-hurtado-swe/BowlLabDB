package DBMS.group6.BowlLabDB.reservation;

import DBMS.group6.BowlLabDB.reservation.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Check if a reservation slot is available for the given time
    public boolean isSlotAvailable(LocalDateTime reservationTime) {
        String sql = "SELECT COUNT(*) FROM Reservations WHERE reservation_time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationTime);
        return count == 0; // Slot is available if no existing reservation
    }

    // Create a reservation for the given time and customer
    public void createReservation(LocalDateTime reservationTime, Integer customerId) {
        String sql = "INSERT INTO Reservations (reservation_time, customer_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, reservationTime, customerId);
    }

    // Find all reserved slots for a given day, excluding customer-specific reservations
    @SuppressWarnings("deprecation")
    public List<LocalDateTime> findReservedSlotsByDay(LocalDate day) {
        LocalDateTime dayStart = day.atTime(17, 0); // Start at 5 PM
        LocalDateTime dayEnd = day.atTime(22, 0);   // End at 10 PM

        String sql = "SELECT reservation_time FROM Reservations WHERE reservation_time BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new Object[]{dayStart, dayEnd}, (rs, rowNum) ->
                rs.getTimestamp("reservation_time").toLocalDateTime()
        );
    }

    // Find all reservations by a specific customer
    @SuppressWarnings("deprecation")
    public List<Reservation> findReservationsByCustomer(Integer customerId) {
        String sql = "SELECT * FROM Reservations WHERE customer_id = ?";
        return jdbcTemplate.query(sql, new Object[]{customerId}, (rs, rowNum) ->
                new Reservation(
                        rs.getInt("id"),
                        rs.getTimestamp("reservation_time").toLocalDateTime(),
                        rs.getInt("customer_id")
                ));
    }
}