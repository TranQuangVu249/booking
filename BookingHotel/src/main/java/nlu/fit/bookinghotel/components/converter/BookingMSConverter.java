package nlu.fit.bookinghotel.components.converter;

import nlu.fit.bookinghotel.DTO.BookingDTO;
import nlu.fit.bookinghotel.entity.Booking;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component

public class BookingMSConverter extends JsonMessageConverter {
    public BookingMSConverter() {
        super();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("nlu.fit.bookinghotel");
        typeMapper.setIdClassMapping(Collections.singletonMap("booking", BookingDTO.class));
        this.setTypeMapper(typeMapper);
    }
}
