package com.rentacar.controller;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.model.enums.Gender;
import com.rentacar.services.CarServiceImpl;
import com.rentacar.services.InsuranceServiceImpl;
import com.rentacar.services.PersonServiceImpl;
import com.rentacar.services.UserRentACarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class RentController {

    @Autowired
    private UserRentACarServiceImpl userRentACarService;
    @Autowired
    private PersonServiceImpl personService;
    @Autowired
    private InsuranceServiceImpl insuranceService;
    @Autowired
    private CarServiceImpl carService;

    public List<Car> cars;
    private CarFilter carFilter;
    private Car car;
    private Insurance insurance;


    @PostConstruct
    public void init() {
        cars = userRentACarService.showAllAvailableCars();
        carFilter = new CarFilter();
        insurance = new Insurance();
    }

    @RequestMapping(value = "/viewCar", method = RequestMethod.POST)
    public String viewCar(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        return carAction(carWinCode, model, request);
    }

    @RequestMapping(value = "/rentCar", method = RequestMethod.POST)
    public String rentCar(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if ("RENT".equals(action) && carWinCode != null) {
            car = userRentACarService.findCarByWinCode(carWinCode);

            /*------------------------------*/
            DrivingLicense drivingLicense = new DrivingLicense();
            drivingLicense.setLicenseNumber("sb434242");
            drivingLicense.setObtainingDate("08-02-2008");
            drivingLicense.setExpiringDate("08-02-2018");

            /*------------------------------*/
            Person person = new Person();
            person.setFirstName("Andrei");
            person.setLastName("Plesca");
            person.setGender(Gender.M);
            person.setBirthDate("23-12-1990");
            person.setPersonalNumber("56156561561661");
            person.setDrivingLicense(drivingLicense);
//            personService.savePerson(person);

            /*------------------------------*/
            car.setAvailability(CarAvailability.RENTED);
//            carService.saveCar(car);
            /*------------------------------*/
            insurance = new Insurance();
            insurance.setCar(car);
            insurance.setPerson(person);
            insurance.setCost();
//            insuranceService.saveInsurance(insurance);
            /*------------------------------*/
            Rent rent = new Rent();
            rent.setActive(true);
            rent.setCar(car);
            rent.setInsurance(insurance);
            rent.setCost();
            rent.setPerson(person);
            userRentACarService.rentACar(rent);
        }
        List<Car> cars = userRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "home";
    }

    @RequestMapping(value = "/bookCar", method = RequestMethod.POST)
    public String bookCar(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        return carAction(carWinCode, model, request);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        List<Car> cars = userRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "home";
    }

    @RequestMapping(value = "filterCars", method = RequestMethod.POST)
    public String filterCars(@ModelAttribute("filter") CarFilter filter, Model model) {
        cars = userRentACarService.searchACar(filter);
        carFilter = filter;
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "filterCars";
    }

    private String carAction(String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        if (carWinCode != null) {
            car = userRentACarService.findCarByWinCode(carWinCode);
            insurance = new Insurance();
            insurance.setCar(car);
        } else {
            List<Car> cars = userRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "home";
        }

        if ("BOOK".equals(action)) {
            model.addAttribute("car", car);
            model.addAttribute("insurance", insurance);
            return "bookCar";
        } else if ("RENT".equals(action)) {
            model.addAttribute("car", car);
            model.addAttribute("insurance", insurance);
            return "rentCar";
        }
        if ("CANCEL".equals(action)) {
            List<Car> cars = userRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "home";
        }
        model.addAttribute("car", car);
        model.addAttribute("insurance", insurance);
        return "viewCar";
    }
}
