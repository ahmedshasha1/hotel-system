package code.hotelservice.hotelservice.dao;

import code.hotelservice.hotelservice.model.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomsRepo extends JpaRepository<Rooms,Long> {
    @Query("SELECT DISTINCT r.roomType FROM Rooms r")
    List<String> findDistinctRoomTypes();


    @Query("SELECT r FROM Rooms r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE" +
            "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
    Page<Rooms> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType,Pageable pageable);


    @Query("SELECT r FROM Rooms r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    Page<Rooms> getAllAvailableRooms(Pageable pageable);

    Rooms findRoomsById(Long id);
}
