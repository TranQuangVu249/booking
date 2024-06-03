package nlu.fit.bookinghotel.repositories;

import nlu.fit.bookinghotel.entity.Hotel;
import nlu.fit.bookinghotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface RoomRepository extends JpaRepository<Room,Long> {

     boolean existsByRoomNumberAndHotelId(String roomNumber, Long hotel_id);

     @Query("SELECT p FROM Room p WHERE " +
             "(:hotelId IS NULL OR :hotelId = 0 OR p.hotel.id = :hotelId) " +
             "AND (:keyword IS NULL OR :keyword = '' OR p.title LIKE %:keyword% OR p.description LIKE %:keyword%)")
     Page<Room> searchRooms
             (@Param("hotelId") Long hotelId,
              @Param("keyword") String keyword, Pageable  pageable);


}
