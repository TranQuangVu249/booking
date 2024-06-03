package nlu.fit.bookinghotel.service.iml;

import nlu.fit.bookinghotel.entity.BookingStatus;
import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.entity.Booking;
import nlu.fit.bookinghotel.entity.User;
import nlu.fit.bookinghotel.exceptions.DataNotFoundException;
import nlu.fit.bookinghotel.repositories.BookingRepository;
import nlu.fit.bookinghotel.repositories.UserRepository;
import nlu.fit.bookinghotel.service.IBookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public Booking createBooking(BookingDTO bookingDTO) throws Exception {
        //tìm xem user'id có tồn tại ko
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("Not Found User by"+bookingDTO.getUserId()));
        //dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(BookingDTO.class,Booking.class)
                .addMappings(mapper-> mapper.skip(Booking::setId));
        // Cập nhật các trường của đơn hàng từ bookingDTO
        Booking booking= new Booking();
        modelMapper.map(bookingDTO,booking);
        booking.setUser(user);
        booking.setIsActive(true);
        booking.setStatus(BookingStatus.PENDING);
        LocalDate checkInDate = bookingDTO.getCheckInDate();
        LocalDate checkoutDate = bookingDTO.getCheckOutDate();
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        booking.setCheck_in_date(checkInDate);
        if (checkoutDate.isBefore(checkInDate)) {
            throw new DataNotFoundException("CheckOutDate must be better than checkInDate !");
        }
        booking.setCheck_out_date(checkoutDate);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long id) {
        return null;
    }

    @Override
    public Booking updateBookingr(Long id, BookingDTO bookingDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteBooking(Long id) {

    }

    @Override
    public List<Booking> findByBooking(Long userId) {
        return null;
    }
}
