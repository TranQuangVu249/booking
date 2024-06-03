package nlu.fit.bookinghotel.controller;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.DTO.HotelDTO;
import nlu.fit.bookinghotel.components.converter.BookingMSConverter;
import nlu.fit.bookinghotel.entity.Booking;
import nlu.fit.bookinghotel.service.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/booking")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO
            , BindingResult result ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            kafkaTemplate.send("booking", bookingDTO);
            this.kafkaTemplate.setMessageConverter(new BookingMSConverter());
            return ResponseEntity.ok("booking");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
