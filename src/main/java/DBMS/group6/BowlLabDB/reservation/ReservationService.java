package DBMS.group6.BowlLabDB.reservation;

import DBMS.group6.BowlLabDB.reservation.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Request a reservation for a customer at a specific date and time
    public boolean requestReservation(LocalDateTime reservationTime, Integer customerId) {
        // Validate time and date constraints
        LocalTime time = reservationTime.toLocalTime();
        if (time.isBefore(LocalTime.of(17, 0)) || time.isAfter(LocalTime.of(21, 59))) {
            return false; // Not within allowed time range
        }

        LocalDate today = LocalDate.now();
        if (reservationTime.toLocalDate().isBefore(today.plusDays(1)) ||
                reservationTime.toLocalDate().isAfter(today.plusDays(7))) {
            return false; // Date not within 1-week range
        }

        // Check if slot is available
        if (reservationRepository.isSlotAvailable(reservationTime)) {
            reservationRepository.createReservation(reservationTime, customerId);
            return true;
        }
        return false;
    }

    // Get available slots for a specific day by excluding reserved slots
    public List<LocalDateTime> getAvailableSlotsByDay(LocalDate day) {
        List<LocalDateTime> reservedSlots = reservationRepository.findReservedSlotsByDay(day);
        List<LocalDateTime> allSlots = new ArrayList<>();

        // Generate all potential slots for the day (5 PM to 10 PM in 1-hour increments)
        for (int hour = 17; hour <= 21; hour++) {
            LocalDateTime slot = day.atTime(hour, 0);
            if (!reservedSlots.contains(slot)) {
                allSlots.add(slot);
            }
        }
        return allSlots;
    }

    // Get all reservations by a specific customer
    public List<Reservation> getCustomerReservations(Integer customerId) {
        return reservationRepository.findReservationsByCustomer(customerId);
    }

    // Get all reservations for a specific day
    public List<Reservation> getDailyReservations(LocalDate day) {
        return reservationRepository.findReservationsByDay(day);
    }

}