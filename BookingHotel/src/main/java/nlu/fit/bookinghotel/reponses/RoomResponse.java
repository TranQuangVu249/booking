package nlu.fit.bookinghotel.reponses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import nlu.fit.bookinghotel.entity.Room;
import nlu.fit.bookinghotel.entity.RoomImage;

import java.util.ArrayList;
import java.util.List;
@Getter
@Builder

public class RoomResponse extends BaseResponse {
    private Long id;
    private String roomNumber;


    private String title;


    private Float price_per_night;


    private Float price_per_hour;

    private String thumbnail;

    private String status;

    private String description;


    private Long hotelId;
    @JsonProperty("product_images")
    private List<RoomImage> roomImageList = new ArrayList<>();;

    public static RoomResponse fromRoom(Room room) {
        RoomResponse productResponse = RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .title(room.getTitle())
                .price_per_hour(room.getPrice_per_hour())
                .price_per_night(room.getPrice_per_night())
                .thumbnail(room.getThumbnail())
                .thumbnail(room.getStatus())
                .description(room.getDescription())
                .hotelId(room.getHotel().getId())
                .roomImageList(room.getRoomImages())
                .build();
        productResponse.setCreatedAt(room.getCreatedAt());
        productResponse.setUpdatedAt(room.getUpdatedAt());
        return productResponse;
    }


}
