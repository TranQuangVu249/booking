package nlu.fit.bookinghotel.service;

import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.entity.Booking;
import nlu.fit.bookinghotel.exceptions.DataNotFoundException;

import java.util.List;

public interface IBookingService {

    Booking createBooking(BookingDTO bookingDTO) throws Exception;
    Booking getBooking(Long id);
    Booking updateBookingr(Long id, BookingDTO bookingDTO) throws DataNotFoundException;
    void deleteBooking(Long id);
    List<Booking> findByBooking(Long userId);
}
