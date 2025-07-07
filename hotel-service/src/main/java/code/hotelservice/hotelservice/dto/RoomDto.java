package code.hotelservice.hotelservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto extends BaseDto{
    private String roomType;
    private Double roomPrice;
    private String logoPath;
    private String description;
    private List<BookingDto> bookings;
}
