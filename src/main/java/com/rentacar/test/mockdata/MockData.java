package com.rentacar.test.mockdata;

import com.rentacar.model.Car;
import com.rentacar.model.DrivingLicense;
import com.rentacar.model.Person;
import com.rentacar.model.enums.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Andrei.Plesca
 */
public class MockData {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static SecureRandom random = new SecureRandom();

    public static Person getMockPerson() throws ParseException {
        Person person = new Person();
        person.setPersonId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@test.com");
        person.setAddress("jd street, apt 5");
        person.setPassword("john1234");
        person.setBirthDate(sdf.parse("20-05-1988"));
        person.setGender(Gender.M);
        //Add Driving License
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setLicenseNumber(new BigInteger(32, random).toString(32).toUpperCase());
        drivingLicense.setObtainingDate(sdf.parse("23-07-2008"));
        drivingLicense.setExpiringDate(sdf.parse("23-07-2018"));

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
}
