package nlu.fit.bookinghotel.repositories;

import nlu.fit.bookinghotel.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail,Long> {
    List<BookingDetail> findByBooking_Id(Long bookingId);
}
