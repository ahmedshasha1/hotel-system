package code.hotelservice.hotelservice.service.mapper;

import code.hotelservice.hotelservice.dto.BookingDto;
import code.hotelservice.hotelservice.dto.RoomDto;
import code.hotelservice.hotelservice.model.Booking;
import code.hotelservice.hotelservice.model.Rooms;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookingMapper {
    BookingMapper booking= Mappers.getMapper(BookingMapper.class);

    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto bookingDto);
    List<BookingDto> toDtoList(List<Booking> booking);

    List<Booking> toEntityList (List<BookingDto> bookingDto);
}
