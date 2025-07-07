package code.hotelservice.hotelservice.dao;

import code.hotelservice.hotelservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    Optional<Booking> findByBookingCode(String confirmationCode);

    boolean existsByBookingCode(String code);
}
