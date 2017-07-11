package com.rentacar.testutils;

import com.rentacar.model.*;
import com.rentacar.model.enums.*;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Andrei.Plesca
 */
public class TestDataUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static SecureRandom random = new SecureRandom();

    public static Person getMockPerson() throws ParseException {
        Person person = new Person();
        person.setPersonId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@test.com");
        person.setAddress("jd street, apt 5");
        person.setUserPassword("john1234");
        person.setCheckPassword("john1234");
        person.setBirthDate(sdf.parse("1988-01-01T00:00:00.000Z"));
        person.setGender(Gender.M);
        //Add Driving License
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setLicenseNumber(new BigInteger(32, random).toString(32).toUpperCase());
        drivingLicense.setObtainingDate(sdf.parse("2012-01-01T00:00:00.000Z"));
        drivingLicense.setExpiringDate(sdf.parse("2022-01-01T00:00:00.000Z"));

        person.setDrivingLicense(drivingLicense);
        return person;
    }

    public static Car getMockCar() throws ParseException {
        Car car = new Car();
        car.setCarId(1L);
        car.setWinCode("TST0000T00TT11111");
        car.setManufacturer("TEST");
        car.setModel("TST");
        car.setType(CarType.COUPE);
        car.setYearOfProduction("2012");
        car.setRegistrationNumber("TEST01");
        car.setEngineVolume(2000);
        car.setFuelType(FuelType.BENZINE);
        car.setTransmission(CarTransmission.AUTOMATIC);
        car.setEconomyClass(EconomyClass.PREMIUM);
        car.setOptions(Options.PREMIUM);
        car.setCarPrice(50.0);
        car.setAvailability(CarAvailability.AVAILABLE);
        return car;
    }

    public static Insurance getMockInsurance() throws ParseException {
        Insurance insurance = new Insurance();
        insurance.setId(1L);
        insurance.setCost(10.0);
        return insurance;
    }

    public static Rent getMockRent() throws ParseException {
        Rent rent = new Rent();
        rent.setRentId(1L);
        rent.setActive(true);
        return rent;
    }

    public static Booking getMockBooking() throws ParseException {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setActive(true);
        return booking;
    }

    public static CarFilter getMockCarFilter() throws ParseException {
        CarFilter carFilter = new CarFilter();
        carFilter.setEconomyClass(EconomyClass.PREMIUM);
        carFilter.setCarType(CarType.COUPE);
        carFilter.setOptions(Options.PREMIUM);
        carFilter.setTransmission(CarTransmission.AUTOMATIC);
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        carFilter.setYearOfProduction("2012");
        return carFilter;
    }

    public static String buildUrlEncodedFormEntity(String... params) {
        if ((params.length % 2) > 0) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            if (i > 0) result.append('&');
            result.append(URLEncoder.encode(params[i]));
            result.append('=');
            result.append(URLEncoder.encode(params[i + 1]));
        }
        return result.toString();
    }
}
