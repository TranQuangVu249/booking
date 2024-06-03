package nlu.fit.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_details")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "price",nullable = false)
    private float price;

    @Column(name = "total_price",nullable = false)
    private float total_price;
}
