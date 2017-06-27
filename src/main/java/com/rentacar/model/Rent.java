package com.rentacar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Andrei.Plesca
 */
@Entity
@Table(name = "rent")
public class Rent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "rentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "carId")
    private Car car;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "personId")
    private Person person;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "insuranceId")
    private Insurance insurance;
    @Column(name = "rentCost", precision = 2)
    private double cost;
    @Column(name = "rentStartDate")
    private LocalDate start;
    @Column(name = "rentEndDate")
    private LocalDate end;
    @Column(name = "rentActive")
    private boolean active;

    public Rent() {
        this.active = true;
    }

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

    public void setCost(double cost) {
        this.cost = cost;
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
