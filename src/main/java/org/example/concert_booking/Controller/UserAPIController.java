package org.example.concert_booking.Controller;

import org.example.concert_booking.Model.Booking;
import org.example.concert_booking.Model.Concert;
import org.example.concert_booking.Model.User;
import org.example.concert_booking.Repository.ConcertRepository;
import org.example.concert_booking.Repository.UserRepository;
import org.example.concert_booking.Security.TokenGenerator;
import java.util.Map;
import java.util.Optional;

import org.example.concert_booking.Service.concertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserAPIController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenGenerator tokenGenerator;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private concertService concertService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("hi");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String token = tokenGenerator.generateToken(user.getEmail(), user.getPassword());
        User role=userRepository.findByEmail(user.getEmail());
        System.out.println("hi"+user.getEmail()+ user.getPassword()+ token);

        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token ,"role", role.getRole()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @GetMapping("/user")
    public  ResponseEntity<?> alluser(){
        return  ResponseEntity.ok(Map.of("user",userRepository.findAll()));
    }

    @PostMapping("/add_concert")
    public ResponseEntity<?> concertCreation(@RequestBody Concert concert){
        concertRepository.save(concert);
        return ResponseEntity.ok("Concert Added Successfully - "+concert.getConcertName());
    }

    @PostMapping("/editConcert/{id}")
    public ResponseEntity<?> editConcert(@RequestBody Concert concert, @PathVariable Integer id){
        return concertService.updateConcert(id, concert)
                .map(updated -> ResponseEntity.ok(Map.of("message", "Updated the concert Successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Concert with ID "+id+" not found")));
    }

    @DeleteMapping("/deleteConcert/{id}")
    public ResponseEntity<?> deleteConcert(@RequestBody Concert concert, @PathVariable Integer id){
        if (concertRepository.existsById(id)){
            concertRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Concert Deleted Successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Concert with ID "+id+" not found"));
        }
    }
    @PostMapping("/createBooking/{concertId}/{userId}")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking, @PathVariable Integer concertId,@PathVariable Integer userId){
        System.out.println("Working Working WORKING");
           boolean resp=    concertService.bookingConcert(concertId, booking ,userId);
           if(resp){
               return  ResponseEntity.ok(Map.of("Concert booked",concertId));
           }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error booking");
    }

    @GetMapping("/listConcerts")
    public ResponseEntity<?> listConcerts(Concert concert){
        return  ResponseEntity.ok(Map.of("concert",concertRepository.findAll()));
    }

    @GetMapping("/singleConcert/{id}")
    public ResponseEntity<?> singleConcert(@PathVariable Integer id) {
//        Optional<Concert> concert = concertRepository.findById(id);
//        if (concert.isPresent()) {
//            Concert Data = concert.get();
//        }
        return  ResponseEntity.ok(Map.of("Concert",concertRepository.findById(id)));
    }
}
