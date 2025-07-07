package code.hotelservice.hotelservice.dto;


import code.hotelservice.hotelservice.dto.auth.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDto extends BaseDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingCode;
    private UserDto user;
    private RoomDto room;
}
