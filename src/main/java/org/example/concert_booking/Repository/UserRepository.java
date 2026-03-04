package org.example.concert_booking.Repository;

import org.example.concert_booking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByToken(String token);
    boolean existsByToken(String token);
}
