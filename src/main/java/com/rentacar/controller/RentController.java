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

import static com.rentacar.util.ModelAttributeConstants.*;
import static com.rentacar.util.PageActionsConstants.*;
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
    private Insurance insurance;

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

    @ModelAttribute(FILTER_MODEL_ATTRIBUTE)
    public CarFilter createFilterModel() {
        return new CarFilter();
    }

    @RequestMapping(value = USER_PAGE_AVAILABLE_CARS_ACTION, method = RequestMethod.GET)
    public String getAvailableCarsPage(Model model) {
        cars = carService.showAllAvailableCars();
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = USER_PAGE_FILTER_CARS_ACTION, method = RequestMethod.POST)
    public String filterCars(@ModelAttribute(FILTER_MODEL_ATTRIBUTE) CarFilter filter, Model model) {
        carFilter = filter;
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        cars = carService.filterCars(filter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = USER_PAGE_CAR_LIST_ACTION_ACTION, method = RequestMethod.GET)
    public String getViewCarPageForm(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkCarWinCode(carWinCode, model);
        model.addAttribute(CAR_MODEL_ATTRIBUTE, car);
        model.addAttribute(INSURANCE_MODEL_ATTRIBUTE, insurance);
        return USER_PAGE_VIEW_CAR;
    }

    @RequestMapping(value = USER_PAGE_CAR_LIST_ACTION_ACTION, params = "rent", method = RequestMethod.GET)
    public String getRentCarPageForm(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkCarWinCode(carWinCode, model);
        model.addAttribute(CAR_MODEL_ATTRIBUTE, car);
        model.addAttribute(INSURANCE_MODEL_ATTRIBUTE, insurance);
        return USER_PAGE_RENT_CAR;
    }

    @RequestMapping(value = USER_PAGE_CAR_LIST_ACTION_ACTION, params = "book", method = RequestMethod.GET)
    public String getBookCarPageForm(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkCarWinCode(carWinCode, model);
        model.addAttribute(CAR_MODEL_ATTRIBUTE, car);
        model.addAttribute(INSURANCE_MODEL_ATTRIBUTE, insurance);
        return USER_PAGE_BOOK_CAR;
    }

    @RequestMapping(value = {USER_PAGE_RENT_CAR_ACTION_ACTION, USER_PAGE_BOOK_CAR_ACTION_ACTION}, params = "cancel", method = RequestMethod.POST)
    public String cancelAction(Model model) {
        cars = carService.filterCars(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = USER_PAGE_RENT_CAR_ACTION_ACTION, params = "rent", method = RequestMethod.POST)
    public String getRentCarAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model, HttpServletRequest request) {
        Person rentPerson = (Person) request.getSession().getAttribute(USER_MODEL_ATTRIBUTE);
        car = carService.findCarByWinCode(carWinCode);
        userRentACarService.rentACar(car, rentPerson);
        cars = carService.filterCars(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = USER_PAGE_BOOK_CAR_ACTION_ACTION, params = "book", method = RequestMethod.POST)
    public String getBookCarAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model, HttpServletRequest request) {
        Person bookPerson = (Person) request.getSession().getAttribute(USER_MODEL_ATTRIBUTE);
        car = carService.findCarByWinCode(carWinCode);
        userRentACarService.bookACar(car, bookPerson);
        cars = carService.filterCars(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return USER_PAGE_AVAILABLE_CARS;
    }

    private void checkCarWinCode(String carWinCode, Model model) {
        if (carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
            insurance = new Insurance();
            insurance.setCar(car);
            insurance.setCost(userRentACarService.insuranceCostCalculate(insurance));
        } else {
            cars = carService.filterCars(carFilter);
            model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
            model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        }
    }
}

