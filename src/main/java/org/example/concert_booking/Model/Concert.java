package org.example.concert_booking.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="ConcertTable")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String concertName;
    private String venue;
    private float price;
    private LocalDate date;
    private Integer availTicket;
    @OneToMany(mappedBy = "concert")
    private List<Booking> bookings;

    public Concert(Integer id, String concertName, String venue, float price, LocalDate date, Integer availTicket) {
        this.id = id;
        this.concertName = concertName;
        this.venue = venue;
        this.price = price;
        this.date = date;
        this.availTicket = availTicket;
    }

    public Concert() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getAvailTicket() {
        return availTicket;
    }

    public void setAvailTicket(Integer availTicket) {
        this.availTicket = availTicket;
    }
}
