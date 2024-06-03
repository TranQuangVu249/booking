package nlu.fit.bookinghotel.repositories;

import nlu.fit.bookinghotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String Phone);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
