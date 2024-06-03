package nlu.fit.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number",nullable = false,length = 20)
    private String roomNumber;

    @Column(name = "room_title",nullable = false,length = 300)
    private String title;

    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(25) DEFAULT 'active'")
    private String status;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description",nullable = false)
    private String  description;

    @Column(nullable = false)
    private Float price_per_night;

    @Column(nullable = false)
    private Float price_per_hour;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomImage> roomImages;


}
