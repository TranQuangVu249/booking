package nlu.fit.bookinghotel.components.Listener;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.service.IBookingService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
private final IBookingService bookingService;
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println(String.format("Consumed message: %s", record.value()));
    }
    @KafkaListener(topics = "booking", groupId = "my-group")
    public void createBooking(ConsumerRecord<String, Object> record) {

    }
}
