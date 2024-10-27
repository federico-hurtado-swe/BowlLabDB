package DBMS.group6.BowlLabDB.reservation;

import DBMS.group6.BowlLabDB.reservation.models.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a new reservation
    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestParam LocalDateTime reservationTime,
                                                    @RequestParam Integer customerId) {
        boolean isReserved = reservationService.requestReservation(reservationTime, customerId);
        if (isReserved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or unavailable reservation slot");
        }
    }

    // Get available reservation slots for a specific day
    @GetMapping("/available")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlotsByDay(@RequestParam LocalDate day) {
        List<LocalDateTime> availableSlots = reservationService.getAvailableSlotsByDay(day);
        return ResponseEntity.ok(availableSlots);
    }

    // Get all reservations by a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Reservation>> getCustomerReservations(@PathVariable Integer customerId) {
        List<Reservation> reservations = reservationService.getCustomerReservations(customerId);
        return ResponseEntity.ok(reservations);
    }
}