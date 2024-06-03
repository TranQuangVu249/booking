package nlu.fit.bookinghotel.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nlu.fit.bookinghotel.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
