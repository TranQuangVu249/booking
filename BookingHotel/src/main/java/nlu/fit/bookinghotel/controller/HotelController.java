package nlu.fit.bookinghotel.controller;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.HotelDTO;
import nlu.fit.bookinghotel.entity.Hotel;
import nlu.fit.bookinghotel.service.IHotelService;
import nlu.fit.bookinghotel.service.iml.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor

public class HotelController {
    private final IHotelService hotelService;

    @PostMapping("/insert")
    public ResponseEntity<?> createHotel(@RequestBody HotelDTO hotelDTO
    ,BindingResult result ){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        hotelService.createHotel(hotelDTO);
        return ResponseEntity.ok("create Hotel success"+hotelDTO);
    }

}
