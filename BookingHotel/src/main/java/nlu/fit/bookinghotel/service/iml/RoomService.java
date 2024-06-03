package nlu.fit.bookinghotel.service.iml;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.RoomDTO;
import nlu.fit.bookinghotel.DTO.RoomImageDTO;
import nlu.fit.bookinghotel.entity.Hotel;
import nlu.fit.bookinghotel.entity.Room;
import nlu.fit.bookinghotel.entity.RoomImage;
import nlu.fit.bookinghotel.exceptions.DataNotFoundException;
import nlu.fit.bookinghotel.exceptions.InvalidParamException;
import nlu.fit.bookinghotel.reponses.RoomResponse;
import nlu.fit.bookinghotel.repositories.HotelRepository;
import nlu.fit.bookinghotel.repositories.RoomImageRepository;
import nlu.fit.bookinghotel.repositories.RoomRepository;
import nlu.fit.bookinghotel.service.IRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    @Override
    public Room createRoom(RoomDTO roomDTO) throws Exception {

        Hotel existingHotel =hotelRepository
                .findById(roomDTO.getHotelId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find room with id: "+roomDTO.getHotelId()));

        if (roomRepository.existsByRoomNumberAndHotelId(roomDTO.getRoomNumber(),existingHotel.getId())) {
            throw new DataNotFoundException("Số phòng đã tồn tại trong khách sạn này");
        }
        Room newRoom= Room.builder()
                .roomNumber(roomDTO.getRoomNumber())
                .title(roomDTO.getTitle())
                .description(roomDTO.getDescription())
                .status("active")
                .price_per_hour(roomDTO.getPrice_per_hour())
                .price_per_night(roomDTO.getPrice_per_night())
                .thumbnail(roomDTO.getThumbnail())
                .hotel(existingHotel)
                .build();

        return roomRepository.save(newRoom);
    }

    @Override
    public Room getRoomById(long id) throws Exception {

        return roomRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Cannot find Room by ID" +id));
    }

    @Override
    public Page<RoomResponse> getAllRooms(String keyword, Long hotelId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<Room> productsPage;
        productsPage = roomRepository.searchRooms(hotelId, keyword,pageRequest);
        return productsPage.map(RoomResponse::fromRoom);
    }

    @Override
    public RoomImage createRoomImage(Long roomId, RoomImageDTO roomImageDTO) throws Exception {
        Room existingRoom = roomRepository
                .findById(roomId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: "+roomImageDTO.getRoomId()));
        RoomImage newRoomImage = RoomImage.builder()
                .room(existingRoom)
                .image_url(roomImageDTO.getImageUrl())
                .build();
        //Ko cho insert quá 5 ảnh cho 1 sản phẩm
        int size = roomImageRepository.findByRoomId(roomId).size();
        if(size >= RoomImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            +RoomImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return roomImageRepository.save(newRoomImage);
    }


    @Override
    public List<Room> findProductsByIds(List<Long> productIds) {
        return null;
    }

    public Page<RoomResponse> getAllRoom(PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang(page) và giới hạn(limit)
        return roomRepository
                .findAll(pageRequest)
                .map(RoomResponse::fromRoom);
    }
}
