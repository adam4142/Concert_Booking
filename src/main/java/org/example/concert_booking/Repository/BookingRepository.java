package org.example.concert_booking.Repository;

import org.example.concert_booking.Model.Booking;
import org.example.concert_booking.Model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
