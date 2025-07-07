package code.hotelservice.hotelservice.controller;

import code.hotelservice.hotelservice.dto.Response;
import code.hotelservice.hotelservice.dto.RoomDto;
import code.hotelservice.hotelservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;


    @GetMapping("/home/pageNumber/{pageNo}/pageSize/{pageSize}")
    ResponseEntity<Response> getAllRooms(@PathVariable int pageNo , @PathVariable int pageSize){
        return ResponseEntity.ok(roomService.getAllRooms( pageNo-1, pageSize));
    }

    @GetMapping("/get-by-id/room-id/{id}")
    ResponseEntity<Response> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/get-available-rooms/pageNumber/{pageNo}/pageSize/{pageSize}")
    ResponseEntity<Response> getAllAvailableRooms(@PathVariable int pageNo , @PathVariable int pageSize){
        return ResponseEntity.ok(roomService.getAllAvailableRooms( pageNo-1, pageSize));
    }

    @GetMapping("/all-room-typies")
    ResponseEntity<List<String>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }

    @GetMapping("/available-rooms-by-date-and-type")
    ResponseEntity<Response> getAvailableRoomsByDataAndType
            ( @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
              @RequestParam(required = false) String roomType, @PathVariable int pageNo , @PathVariable int pageSize){

        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null) {
            throw new RuntimeException("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
        }
        return ResponseEntity.ok(roomService.getAvailableRoomsByDataAndType( checkInDate,checkOutDate,roomType,pageNo-1, pageSize));
    }

    @PostMapping("/new-room")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> createRoom(@RequestBody @Validated RoomDto roomDto){
        roomService.addNewRoom(roomDto);
        return ResponseEntity.created(URI.create("/room/new-room")).build();
    }

    @PutMapping("/update-room")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> updateRoom(@RequestBody @Validated RoomDto roomDto){
        roomService.updateRoom(roomDto);
        return ResponseEntity.created(URI.create("/room/new-room")).build();
    }

    @DeleteMapping("/delete-room/room-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<String> deleteRoom(@PathVariable @Validated Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.accepted().build();
    }







}
