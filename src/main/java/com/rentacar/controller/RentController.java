package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Insurance;
import com.rentacar.model.Person;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.services.CarService;
import com.rentacar.services.UserRentACarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.rentacar.util.PageNavigationConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class RentController {
    private final UserRentACarService userRentACarService;
    private final CarService carService;

    private List<Car> cars;
    private CarFilter carFilter;
    private Car car;

    @Autowired
    public RentController(UserRentACarService userRentACarService, CarService carService) {
        this.userRentACarService = userRentACarService;
        this.carService = carService;
    }

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
        cars = carService.filterCars(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = "/bookCar.do", method = RequestMethod.POST)
    public String bookCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        Person bookPerson = (Person) request.getSession().getAttribute("user");
        if ("BOOK".equals(action) && carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            userRentACarService.bookACar(car, bookPerson);
        }
        cars = carService.filterCars(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = "/availableCars", method = RequestMethod.GET)
    public String getAvailableCarsPage(Model model) {
        cars = carService.showAllAvailableCars();
        model.addAttribute("cars", cars);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = "/filterCars", method = RequestMethod.POST)
    public String filterCars(@ModelAttribute("filter") CarFilter filter, Model model) {
        carFilter = filter;
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        cars = carService.filterCars(filter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    private String carAction(String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        Insurance insurance;
        if (carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            insurance = new Insurance();
            insurance.setCost(userRentACarService.insuranceCostCalculate(insurance));
        } else {
            cars = carService.filterCars(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return USER_PAGE_AVAILABLE_CARS;
        }

        if ("BOOK".equals(action)) {
            model.addAttribute("car", car);
            model.addAttribute("insurance", insurance);
            return USER_PAGE_BOOK_CAR;
        } else if ("RENT".equals(action)) {
            model.addAttribute("car", car);
            model.addAttribute("insurance", insurance);
            return USER_PAGE_RENT_CAR;
        }
        if ("CANCEL".equals(action)) {
            cars = carService.filterCars(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return USER_PAGE_AVAILABLE_CARS;
        }
        model.addAttribute("car", car);
        model.addAttribute("insurance", insurance);
        return USER_PAGE_VIEW_CAR;
    }
}

