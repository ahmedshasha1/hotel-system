package code.hotelservice.hotelservice.service.impl;

import code.hotelservice.hotelservice.dao.RoomsRepo;
import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.dto.RoomDto;
import code.hotelservice.hotelservice.model.Rooms;
import code.hotelservice.hotelservice.service.RoomService;
import code.hotelservice.hotelservice.service.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomsRepo roomsRepo;
    @Override
    public Response addNewRoom(RoomDto roomDto) {
        if (Objects.nonNull(roomDto.getId())){
            throw new RuntimeException("id.mustnull");
        }
        Response response = new Response();
        RoomDto rooms = RoomMapper.roomMapper.toDto(roomsRepo.save(RoomMapper.roomMapper.toEntity(roomDto)));
        response.setRoom(rooms);
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomsRepo.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by(Sort.Direction.DESC, "id"));
        Response response = new Response();
        Page<Rooms> rooms = roomsRepo.findAll(pageable);
        response.setRoomList(RoomMapper.roomMapper.toDtoList(rooms.getContent()));
        return response;
    }

    @Override
    public void deleteRoom(Long roomId) {

        if(Objects.isNull(roomId)){
            throw new RuntimeException("invalid.id");
        }
        if (roomsRepo.existsById(roomId)){
            throw new RuntimeException("room.notfound");
        }
        roomsRepo.deleteById(roomId);
    }

    @Override
    public Response updateRoom(RoomDto roomDto) {
        if (Objects.isNull(roomDto.getId())){
            throw new RuntimeException("id.mustnotnull");
        }

        if(!roomsRepo.existsById(roomDto.getId())){
            throw new RuntimeException("id.noutfound");
        }

        Response response = new Response();

        RoomDto rooms = RoomMapper.roomMapper.toDto(roomsRepo.save(RoomMapper.roomMapper.toEntity(roomDto)));
        response.setRoom(rooms);
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {

        if (Objects.isNull(roomId)){
            throw new RuntimeException("id is null");
        }
        Response response = new Response();

        RoomDto room =RoomMapper.roomMapper.toDto(roomsRepo.findRoomsById(roomId));

        if(room == null){
            throw new RuntimeException("id.noutfound");
        }
        response.setRoom(room);
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType,int pageNo,int pageSize) {
        if (checkInDate.equals(null)){
            throw new RuntimeException("enter.date");
        }
        if (checkOutDate.equals(null)){
            throw new RuntimeException("enter.date");
        }
        if(Objects.isNull(roomType)){
            throw new RuntimeException("Enter.roomType");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Response response =new Response();
        Page<Rooms> rooms =roomsRepo.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType,pageable);
        response.setRoomList(RoomMapper.roomMapper.toDtoList(rooms.getContent()));
        return response;
    }

    @Override
    public Response getAllAvailableRooms(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        Response response = new Response();
        Page<Rooms> rooms = roomsRepo.getAllAvailableRooms(pageable);
        response.setRoomList(RoomMapper.roomMapper.toDtoList(rooms.getContent()));
        return response;
    }
}
