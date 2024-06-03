package nlu.fit.bookinghotel.service;


import nlu.fit.bookinghotel.DTO.RoomDTO;
import nlu.fit.bookinghotel.DTO.RoomImageDTO;
import nlu.fit.bookinghotel.entity.Room;
import nlu.fit.bookinghotel.entity.RoomImage;
import nlu.fit.bookinghotel.reponses.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IRoomService  {
    Room createRoom(RoomDTO roomDTO) throws Exception;
    Room getRoomById(long id) throws Exception;
    Page<RoomResponse> getAllRooms(String keyword,
                                                Long hotelId, PageRequest pageRequest);
//    Room updateProduct(long id, ProductDTO productDTO) throws Exception;
//    void deleteProduct(long id);
//    boolean existsByName(String name);
    RoomImage createRoomImage(
            Long roomId,
            RoomImageDTO roomImageDTO) throws Exception;

    List<Room> findProductsByIds(List<Long> productIds);


}
