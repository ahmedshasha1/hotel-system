package code.hotelservice.hotelservice.service;

import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Response addNewRoom(RoomDto roomDto);

    List<String> getAllRoomTypes();

    Response getAllRooms(int pageNo,int pageSize);

    void deleteRoom(Long roomId);

    Response updateRoom(RoomDto roomDto);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType,int pageNo,int pageSize);

    Response getAllAvailableRooms(int pageNo,int pageSize);
}
