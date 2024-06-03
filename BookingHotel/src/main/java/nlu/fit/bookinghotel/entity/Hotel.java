package nlu.fit.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "hotel")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "district")
    private String district;

    @Column(name = "city")
    private String city;
}
