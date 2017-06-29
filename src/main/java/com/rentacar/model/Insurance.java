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
    private long id;
    @Column(name = "insuranceCost", precision = 2)
    private double cost;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "carId")
    private Car car;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "personId")
    private Person person;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Insurance insurance = (Insurance) o;

        if (id != insurance.id) return false;
        if (Double.compare(insurance.cost, cost) != 0) return false;
        if (car != null ? !car.equals(insurance.car) : insurance.car != null) return false;
        return person != null ? person.equals(insurance.person) : insurance.person == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "\n id=" + id +
                ",\n cost=" + cost +
                ",\n car=" + car +
                ",\n person=" + person +
                "\n}";
    }
}
