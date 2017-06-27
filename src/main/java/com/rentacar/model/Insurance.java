package com.rentacar.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andrei.Plesca
 */

@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "insuranceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "insuranceCost", precision = 2)
    private double cost;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId")
    private Person person;

    public int getId() {
        return id;
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
}
