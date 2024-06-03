package nlu.fit.bookinghotel.DTO;

import jakarta.persistence.Column;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDTO {

    private String name;
    private String apartmentNumber;
    private String street;
    private String district;
    private String city;
}
