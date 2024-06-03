package nlu.fit.bookinghotel.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomImageDTO {
    @JsonProperty("room_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long roomId;

    @Size(min = 5, max = 200, message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;

}
