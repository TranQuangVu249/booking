package nlu.fit.bookinghotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number",nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "check_in_date",nullable = false)
    private LocalDate check_in_date;

    @Column(name = "check_out_date",nullable = false)
    private LocalDate check_out_date;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_method")
    private String payment_method;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "is_active")
    private Boolean isActive;
}
