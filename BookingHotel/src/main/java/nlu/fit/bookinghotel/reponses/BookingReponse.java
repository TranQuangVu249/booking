package nlu.fit.bookinghotel.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import nlu.fit.bookinghotel.entity.User;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingReponse extends BaseResponse {

    private Long id;

    @JsonProperty("user_id")
    private User user;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("note")
    private String note;

    @JsonProperty("check_in_date")
    private Date check_in_date;

    @JsonProperty("check_out_date")
    private Date check_out_date;

    @JsonProperty("status")
    private String status;

    @JsonProperty("Payment_method")
    private String Payment_method;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("is_active")
    private Boolean isActive;
}
