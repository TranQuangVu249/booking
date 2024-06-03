package nlu.fit.bookinghotel.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultipleRoomNumberDTO {
    @JsonProperty("room_numbers")
    @NotEmpty(message = "At least one room number is required")
    private List<String> roomNumbers;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "title must be between 3 and 200 characters")
    private String title;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Price must be less than or equal to 10,000,000")
    private Float price_per_night;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 1000000, message = "Price must be less than or equal to 1,000,000")
    private Float price_per_hour;

    private String thumbnail;

    private String description;

    @JsonProperty("hotel_id")
    private Long hotelId;
}