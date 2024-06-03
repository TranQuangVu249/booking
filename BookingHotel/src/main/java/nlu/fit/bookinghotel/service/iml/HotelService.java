package nlu.fit.bookinghotel.service.iml;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.HotelDTO;
import nlu.fit.bookinghotel.entity.Hotel;
import nlu.fit.bookinghotel.repositories.HotelRepository;
import nlu.fit.bookinghotel.service.IHotelService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;
    @Override
    public Hotel createHotel(HotelDTO hotelDTO) {
        Hotel newHotel = Hotel.builder()
                .name(hotelDTO.getName())
                .apartmentNumber(hotelDTO.getApartmentNumber())
                .street(hotelDTO.getStreet())
                .district(hotelDTO.getDistrict())
                .city(hotelDTO.getCity())
                .build();

        return  hotelRepository.save(newHotel);
    }

    @Override
    public Hotel getHotelById(long id) {
        return hotelRepository.findById(id).orElseThrow(()-> new RuntimeException("Hotel not found"));
    }

    @Override
    public List<Hotel> getAllHotel() {
        return  hotelRepository.findAll();
    }

    @Override
    public Hotel updateHotel(long hotelId, HotelDTO hotel) {
        Hotel existingHotel= getHotelById(hotelId);
        return null;
    }

    @Override
    public void deleteHotel(long id) {

    }
}
