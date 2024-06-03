package nlu.fit.bookinghotel.components.Listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.entity.Booking;
import nlu.fit.bookinghotel.service.IBookingService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(id = "my-group", topics = { "booking" })
public class MyKafkaListener {
    private final IBookingService bookingService;
    @KafkaHandler
    public void listenCategory(BookingDTO booking) throws Exception {
        log.info("createBooking "+ booking);
        bookingService.createBooking(booking);

    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
    @KafkaHandler
    public void listenListOfCategories(List<Booking> bookingList) {
        System.out.println("Received: " + bookingList);
    }

}
