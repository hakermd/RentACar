package com.rentacar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by aplesca on 5/8/2017.
 */
@Entity
@Table(name = "rent")
public class Rent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personPersonalNumber")
    private Person person;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "insuranceId")
    private Insurance insurance;
    @Column(name = "rentCost")
    private double cost;
    @Column(name = "rentStartDate")
    private LocalDate start;
    @Column(name = "rentEndDate")
    private LocalDate end;
    @Column(name = "rentActive")
    private boolean active = true;

    public long getRentId() {
        return rentId;
    }

    public void setRentId(long rentId) {
        this.rentId = rentId;
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

    public double getCost() {
        return cost;
    }

    public void setCost() {
        this.cost = this.car.getCarPrice() + this.insurance.getCost();
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
