package code.hotelservice.hotelservice.controller;

import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.model.Booking;
import code.hotelservice.hotelservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/Booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/save-booking/room-id/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> saveBooking(@PathVariable Long roomId,@RequestBody @Validated Booking bookingRequest){
        bookingService.saveBooking(roomId,bookingRequest);
        return ResponseEntity.created(URI.create("/booking/new-booking/")).build();
    }

    @GetMapping("/booking-by-code")
    ResponseEntity<Response> findBookingByConfirmationCode(@RequestBody @Validated String code){
        return ResponseEntity.ok(bookingService.findBookingByConfirmationCode(code));
    }
    @GetMapping("/all-bookings")
    ResponseEntity<Response> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    @DeleteMapping("/cancel-booking/booking-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    ResponseEntity<String> cancelBooking (@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.accepted().build();
    }
}
