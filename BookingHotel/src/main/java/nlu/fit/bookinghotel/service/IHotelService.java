package nlu.fit.bookinghotel.service;

import nlu.fit.bookinghotel.DTO.HotelDTO;
import nlu.fit.bookinghotel.entity.Hotel;

import java.util.List;

public interface IHotelService {
    Hotel createHotel(HotelDTO category);
    Hotel getHotelById(long id);
    List<Hotel> getAllHotel();
    Hotel updateHotel(long hotelId, HotelDTO category);
    void deleteHotel(long id);

}
