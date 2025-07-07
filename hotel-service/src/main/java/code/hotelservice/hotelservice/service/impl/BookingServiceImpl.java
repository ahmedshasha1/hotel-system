package code.hotelservice.hotelservice.service.impl;

import code.hotelservice.hotelservice.dao.BookingRepo;
import code.hotelservice.hotelservice.dao.RoomsRepo;
import code.hotelservice.hotelservice.dao.auth.UserRepo;
import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.model.Booking;
import code.hotelservice.hotelservice.model.Rooms;
import code.hotelservice.hotelservice.model.auth.User;
import code.hotelservice.hotelservice.service.BookingService;
import code.hotelservice.hotelservice.service.mapper.BookingMapper;
import code.hotelservice.hotelservice.utils.ConfirmationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private RoomsRepo roomsRepo;


    @Override
    public Response saveBooking(Long roomId, Booking bookingRequest) {
        if(Objects.isNull(roomId) ){
            throw new RuntimeException("id.mustnotnull");
        }

        if(Objects.nonNull(bookingRequest.getId())){
            throw new RuntimeException("id.mustnull");
        }

        Response response = new Response();

        Rooms rooms = roomsRepo.findRoomsById(roomId);
        if (rooms == null){
            throw new RuntimeException("room.notfound");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User users= (User) authentication.getPrincipal();

        List<Booking> existingBookings = rooms.getBookings();
        if (!roomIsAvailable(bookingRequest, existingBookings)) {
            throw new RuntimeException("Room not Available for selected date range");
        }


        String code = ConfirmationCode.confirmationCode();
        while (bookingRepo.existsByBookingCode(code)){
            code = ConfirmationCode.confirmationCode();
        }
        bookingRequest.setBookingCode(code);
        bookingRequest.setUser(users);
        bookingRequest.setRoom(rooms);
        bookingRepo.save(bookingRequest);

        response.setBookingConfirmationCode(code);
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        if(Objects.isNull(confirmationCode) ){
            throw new RuntimeException("code.mustnotnull");
        }
        Response response = new Response();
        Booking booking = bookingRepo.findByBookingCode(confirmationCode)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));
        response.setBooking(BookingMapper.booking.toDto(booking));
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        List<Booking> bookings = bookingRepo.findAll((Sort.by(Sort.Direction.DESC, "id")));
        response.setBookingList(BookingMapper.booking.toDtoList(bookings));
        return response;
    }

    @Override
    public void cancelBooking(Long bookingId) {
        if(Objects.isNull(bookingId)){
            throw new RuntimeException("invalid.id");
        }
        if (bookingRepo.existsById(bookingId)){
            throw new RuntimeException("booking.notfound");
        }
        bookingRepo.deleteById(bookingId);

    }



    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
