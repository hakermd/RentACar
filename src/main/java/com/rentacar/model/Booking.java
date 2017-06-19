package com.rentacar.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by Andrei.Plesca
 */
@Entity
@Table(name = "booking", uniqueConstraints = {
        @UniqueConstraint(columnNames = "bookingId"),
        @UniqueConstraint(columnNames = "bookingCode")})
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "bookingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;
    @Column(name = "bookingDate")
    private Date bookDate;
    @Column(name = "bookingCost", nullable = false, columnDefinition = "double default 5.0")
    private double cost;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "carId")
    private Car car;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "personId")
    private Person person;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "insuranceId")
    private Insurance insurance;
    @Column(name = "bookingCode", nullable = false, unique = true)
    private String bookingCode;
    @Column(name = "bookingActive", nullable = false, columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;
    @Transient
    private SecureRandom random = new SecureRandom();

    public Booking() {
        this.bookingCode = new BigInteger(32, random).toString(32).toUpperCase();
        this.active = true;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SecureRandom getRandom() {
        return random;
    }

    public void setRandom(SecureRandom random) {
        this.random = random;
    }
}
