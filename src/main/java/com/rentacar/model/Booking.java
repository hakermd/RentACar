package com.rentacar.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by aplesca on 5/10/2017.
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
    private int id;
    @Column(name = "bookingDate")
    private Date bookDate;
    @Column(name = "bookingCost", nullable = false, columnDefinition = "double default 5.0")
    private double cost;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personPersonalNumber")
    private Person person;
    @Column(name = "bookingCode", nullable = false, unique = true)
    private String bookingCode;
    @Column(name = "bookingActive", nullable = false, columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active = true;
    @Transient
    private SecureRandom random = new SecureRandom();

    public String bookACar(Car car) {
        return new BigInteger(32, random).toString(32);
    }

    public void cancelBookingByCode(String code) {
        //TODO
    }

    public int getId() {
        return id;
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
