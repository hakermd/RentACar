package com.rentacar.model;

import com.rentacar.model.enums.EconomyClass;
import com.rentacar.model.enums.Options;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by aplesca on 5/8/2017.
 */

@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "insuranceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "insuranceCost")
    private double cost;
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personPersonalNumber")
    private Person person;

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost() {
        this.cost = insuranceCostCalculate(this.car.getEconomyClass());
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

    public double insuranceCostCalculate(EconomyClass economyClass) {
        switch (economyClass) {
            case ECONOMY:
                return 0.3 * this.car.getCarPrice();
            case PREMIUM:
                return 0.5 * this.car.getCarPrice();
            case BUSINESS:
                return 0.7 * this.car.getCarPrice();
        }
        return 0;
    }
}
