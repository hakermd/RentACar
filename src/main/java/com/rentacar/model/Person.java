package com.rentacar.model;

import com.rentacar.model.enums.Gender;
import com.rentacar.model.enums.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Andrei.Plesca
 */
@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "licenseNumber")})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "personId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "userPassword", nullable = false)
    private String userPassword;
    @Transient
    private String checkPassword;
    @Column(name = "birthDate")
    private Date birthDate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "userRole", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;
    @Embedded
    private DrivingLicense drivingLicense;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return (personId != null ? personId.equals(person.personId) : person.personId == null)
                && (firstName != null ? firstName.equals(person.firstName) : person.firstName == null)
                && (lastName != null ? lastName.equals(person.lastName) : person.lastName == null)
                && (email != null ? email.equals(person.email) : person.email == null)
                && (address != null ? address.equals(person.address) : person.address == null)
                && (userPassword != null ? userPassword.equals(person.userPassword) : person.userPassword == null)
                && (birthDate != null ? birthDate.equals(person.birthDate) : person.birthDate == null)
                && gender == person.gender && userRole == person.userRole
                && (drivingLicense != null ? drivingLicense.equals(person.drivingLicense) : person.drivingLicense == null);
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (checkPassword != null ? checkPassword.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + userRole.hashCode();
        result = 31 * result + (drivingLicense != null ? drivingLicense.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "\n personId=" + personId +
                ",\n  firstName='" + firstName + '\'' +
                ",\n  lastName='" + lastName + '\'' +
                ",\n  email='" + email + '\'' +
                ",\n  address='" + address + '\'' +
                ",\n  birthDate=" + birthDate +
                ",\n  gender=" + gender +
                ",\n  userRole=" + userRole +
                ",\n  drivingLicense=" + drivingLicense +
                "\n }";
    }
}
