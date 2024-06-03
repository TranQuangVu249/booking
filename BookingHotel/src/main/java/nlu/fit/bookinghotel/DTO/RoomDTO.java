package nlu.fit.bookinghotel.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Getter;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDTO {
    @JsonProperty("room_number")
    @NotBlank(message = "RoomNumber is required")
    @Size(min = 3, max = 10, message = "Name must be between 3 and 10 characters")
    private String roomNumber;

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
