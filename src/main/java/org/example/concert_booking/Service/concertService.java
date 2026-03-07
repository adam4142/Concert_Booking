package org.example.concert_booking.Service;

import jakarta.transaction.Transactional;
import org.example.concert_booking.Model.Booking;
import org.example.concert_booking.Model.Concert;
import org.example.concert_booking.Model.User;
import org.example.concert_booking.Repository.BookingRepository;
import org.example.concert_booking.Repository.ConcertRepository;
import org.example.concert_booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class concertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<Concert> updateConcert(Integer id, Concert updatedData) {
        return concertRepository.findById(id).map(existingConcert -> {
            existingConcert.setConcertName(updatedData.getConcertName());
            existingConcert.setDate(updatedData.getDate());
            existingConcert.setAvailTicket(updatedData.getAvailTicket());
            existingConcert.setPrice(updatedData.getPrice());
            existingConcert.setVenue(updatedData.getVenue());

            return concertRepository.save(existingConcert);
        });


    }
//
//   public boolean bookingConcert(Integer concert_id, Booking booking ,Integer user_id) {
//       Optional<User> user = userRepository.findById(user_id);
//       User res = new User();
//       if (user.isPresent()) {
//           res = user.get();
//       }
//       Optional<Concert> concert = concertRepository.findById(concert_id);
//       if (concert.isPresent()) {
//           Concert data = concert.get();
//           int booked = booking.getNoOfTicketsBooked();
//           if (booked > 3) {
//               return false;
//           } else {
//               float totalPrice = booked * data.getPrice();
//               booking.setTotalPrice(totalPrice);
//               booking.setConcert(data);
//               booking.setUser(res);
//               data.setAvailTicket(data.getAvailTicket() - booked);
//
//               concertRepository.save(data);
//               bookingRepository.save(booking);
//               return true;
//           }
//       }
//       return false;
    @Transactional
    public boolean bookingConcert(Integer concertId, Booking booking, Integer userId){
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Concert> concertOpt = concertRepository.findById(concertId);

        if (userOpt.isEmpty() || concertOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        Concert concert = concertOpt.get();
        int numTickets = booking.getNoOfTicketsBooked();
        System.out.println("Working Working WORKING 1");

        if (numTickets > 3 || numTickets > concert.getAvailTicket()) {
            throw new RuntimeException("Limit exceeded: You can only book upto 3 Tickets or Insufficient Tickets");
        }
        System.out.println("Working Working WORKING 2");
        float totalPrice = numTickets * concert.getPrice();
        booking.setTotalPrice(totalPrice);
        booking.setUser(user);
        booking.setConcert(concert);

        concert.setAvailTicket(concert.getAvailTicket() - numTickets);
        concertRepository.save(concert);
        bookingRepository.save(booking);

        return true;
    }




    }


