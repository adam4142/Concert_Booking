package org.example.concert_booking.Controller;

import org.example.concert_booking.Model.User;
import org.example.concert_booking.Repository.UserRepository;
import org.example.concert_booking.Security.TokenGenerator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token ,"role", role.getRole()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @GetMapping("/user")
    public  ResponseEntity<?> alluser(){
        return  ResponseEntity.ok(Map.of("user",userRepository.findAll()));
    }
}