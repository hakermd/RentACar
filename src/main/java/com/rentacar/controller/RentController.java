package com.rentacar.controller;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory
            .getLogger(RentController.class);

    @Autowired
    private UserRentACarService userRentACarService;
    @Autowired
    private CarService carService;

    public List<Car> cars;
    private CarFilter carFilter;
    private Car car;
    private Insurance insurance;


    @PostConstruct
    public void init() {
        carFilter = new CarFilter();
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
    }

    @ModelAttribute("filter")
    public CarFilter createFilterModel() {
        return new CarFilter();
    }

    @RequestMapping(value = "/carListAction", method = RequestMethod.GET)
    public String viewCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        return carAction(carWinCode, model, request);
    }

    @RequestMapping(value = "/rentCar.do", method = RequestMethod.POST)
    public String rentCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        Person rentPerson = (Person) request.getSession().getAttribute("user");
        if ("RENT".equals(action) && carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            userRentACarService.rentACar(car, rentPerson);
        }
        List<Car> cars = carService.filterCars(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "availableCars";
    }

    @RequestMapping(value = "/bookCar.do", method = RequestMethod.POST)
    public String bookCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        Person bookPerson = (Person) request.getSession().getAttribute("user");
        if ("BOOK".equals(action) && carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            userRentACarService.bookACar(car, bookPerson);
        }
        List<Car> cars = carService.filterCars(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "availableCars";
    }

    @RequestMapping(value = "/availableCars", method = RequestMethod.GET)
    public String getAvailableCarsPage(Model model) {
        List<Car> cars = carService.showAllAvailableCars();
        model.addAttribute("cars", cars);
        return "availableCars";
    }

    @RequestMapping(value = "/filterCars", method = RequestMethod.POST)
    public String filterCars(@ModelAttribute("filter") CarFilter filter, Model model) {
        carFilter = filter;
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        cars = carService.filterCars(filter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "availableCars";
    }

    private String carAction(String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        if (carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            insurance = new Insurance();
            insurance.setCar(car);
            insurance.setCost(userRentACarService.insuranceCostCalculate(insurance));
        } else {
            List<Car> cars = carService.filterCars(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "availableCars";
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
            List<Car> cars = carService.filterCars(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "availableCars";
        }
        model.addAttribute("car", car);
        model.addAttribute("insurance", insurance);
        return "viewCar";
    }
}

