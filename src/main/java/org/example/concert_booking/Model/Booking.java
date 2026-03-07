package org.example.concert_booking.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "concert_id" ,nullable=false)
    private Concert concert;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private int noOfTicketsBooked;
    private float totalPrice;

    public Booking(int noOfTicketsBooked) {
        this.noOfTicketsBooked = noOfTicketsBooked;

    }

    public Booking() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNoOfTicketsBooked() {
        return noOfTicketsBooked;
    }

    public void setNoOfTicketsBooked(int noOfTicketsBooked) {
        this.noOfTicketsBooked = noOfTicketsBooked;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
