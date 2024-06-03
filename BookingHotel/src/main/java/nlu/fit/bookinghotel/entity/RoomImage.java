package nlu.fit.bookinghotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomImage {
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 6;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @Column(name = "image_url",length = 300)
    private String image_url;
}
