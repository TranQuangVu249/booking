package nlu.fit.bookinghotel.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {

    @Min(value = 1, message = "User's ID must be > 0")
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    private String email;
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, message = "Phone number must be at least 5 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String note;

    @JsonProperty("check_in_date")
    private LocalDate checkInDate;

    @JsonProperty("check_out_date")
    private LocalDate checkOutDate;

    private String status;

    @JsonProperty("payment_method")
    private String payment_method;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("is_active")
    private Boolean isActive;
}
