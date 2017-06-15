package com.rentacar.model;

import com.rentacar.model.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrei.Plesca
 */
@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = "personalNumber"),
        @UniqueConstraint(columnNames = "licenseNumber") })
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "personalNumber")
    private String personalNumber;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDate")
    private Date birthDate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private DrivingLicense drivingLicense;
    @Column(name = "address")
    private String address;


    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            this.birthDate = fmt.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
