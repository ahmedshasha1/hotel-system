package code.hotelservice.hotelservice.service;


import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.model.Booking;

public interface BookingService {
    Response saveBooking(Long roomId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    void cancelBooking(Long bookingId);


}

