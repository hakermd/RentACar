package com.rentacar.services;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
public class AdminRentACarServiceImpl implements AdminRentACarService {
    @Autowired
    private CarDao carDao;
//    @Autowired
//    private InsuranceDao insuranceDao;
/*
    public static void main(String[] args) {
        System.out.println("Hibernate one to one (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime iaka = LocalDateTime.of(2015,12,28,12,33);
        LocalDate iakaDate = LocalDate.of(2017, 1, 15);
//        LocalTime iakaTime = LocalTime.of(15, 22, 55);
//        iaka = LocalDateTime.of(iakaDate, iakaTime);
//
//        System.out.println(now);

        session.beginTransaction();

        Car car = new Car();

        car.setWinCode("JTDKN3DU7C5497211");
        car.setManufacturer("BMW");
        car.setModel("520");
        car.setType(CarType.SEDAN);
        car.setYearOfProduction("1999");
        car.setRegistrationNumber("B312AT");
        car.setEngineVolume(1991);
        car.setFuelType(FuelType.BENZINE);
        car.setTransmission(CarTransmission.MANUAL);
        car.setEconomyClass(EconomyClass.PREMIUM);
        car.setOptions(Options.PREMIUM);
        car.setCarPrice();
        car.setAvailability(CarAvailability.AVAILABLE);

        //-----------------------------------------------------
        Person person = new Person();
        person.setPersonalNumber("12564166466464");
        person.setFirstName("Andrei");
        person.setLastName("Plesca");
        person.setBirthDate("23-12-1990");
        person.setGender(Gender.M);
        person.setAddress("Chisinau, Fierarilor 17");

        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setLicenseNumber("B3214YU53");
        drivingLicense.setObtainingDate("23-02-2008");
        drivingLicense.setExpiringDate("23-02-2018");
        person.setDrivingLicense(drivingLicense);


        //-----------------------------------------------------
        Insurance insurance = new Insurance();
//        insurance.setId(1);
        insurance.setCar(car);
        insurance.setCost();
        insurance.setPerson(person);


        //-----------------------------------------------------
        Rent rent = new Rent();
        rent.setCar(car);
        rent.setPerson(person);
        rent.setInsurance(insurance);
        rent.setCost();
        rent.setStart(iakaDate);
        rent.setEnd(iakaDate);
        rent.setActive(true);

        Rent _rent = new Rent();
        _rent.setCar(car);
        _rent.setPerson(person);
        _rent.setInsurance(insurance);
        _rent.setStart(iakaDate);
        _rent.setEnd(iakaDate);
        _rent.setCost();
        _rent.setActive(true);
        //-----------------------------------------------------

        session.save(car);
//        session.save(person);
//        session.save(insurance);
        session.save(rent);
        session.save(_rent);
        session.getTransaction().commit();
        System.out.println("Done");

    }
    */

    public List<Car> addACar(Car car) {
        return null;
    }

    public void removeACar(Car car) {

    }

    public List<Car> suspendACar(Car car) {
        return null;
    }

    public List<Car> unsuspendACar(Car car) {
        return null;
    }

    public List<Car> getAllCars() {
        List<Car> cars = carDao.searchACarByStatus(CarAvailability.AVAILABLE);
//        List<Car> cars = carDao.findAll();
        for (Car c : cars) {
            System.out.println("-----------------" + c.getModel());
        }
//        List<Insurance> insurances = insuranceDao.findAll();
//        for (Insurance i:insurances) {
//            System.out.println("-----------------"+i.getId());
//        }
//        return (List<Car>) insurances.get(0).getCar();
        return cars;
    }

    //    public static void main(String[] args) {
//
//        for (int i = 1; i<101;i++){
//            StringBuffer sb = new StringBuffer();
//            sb.append("");
//            if (i%3==0){sb.append("Fizz");}
//            if (i%5==0){sb.append("Buzz");}
//            if(sb.length()==0) {
//                System.out.print(i+" ");
//            }else{ System.out.print(sb+ " ");}
//        }
//    }
    public List<Car> searchACar(Car car) {
        return null;
    }

    public List<Car> changeACarInfo(Car car) {
        return null;
    }

    public List<Car> searchACarByStatus(Car car) {
        return null;
    }
}
