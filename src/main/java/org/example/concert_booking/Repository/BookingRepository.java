package org.example.concert_booking.Repository;

import org.example.concert_booking.Model.Booking;
import org.example.concert_booking.Model.Concert;
import org.example.concert_booking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("""
        SELECT new map(
            b.concert.concertName as name, 
            b.concert.venue as venue, 
            b.totalPrice as total
        ) 
        FROM Booking b 
        WHERE b.user.id = :userId
    """)
    List<Map<String, Object>> findRawDataByUserId(@Param("userId") Integer userId);
}