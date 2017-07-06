package com.rentacar.controller;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.services.UserRentACarService;
import com.rentacar.validator.CarValidator;
import com.rentacar.validator.EditCarValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class AdminController {
    @Autowired
    private AdminRentACarService adminRentACarService;
    @Autowired
    private CarService carService;
    @Autowired
    private UserRentACarService rentACarService;
    @Autowired
    private CarValidator carValidator;
    @Autowired
    private EditCarValidator editCarValidator;


    public List<Car> cars;
    private CarFilter carFilter;
    private Car car;
    private static final Logger logger = LoggerFactory
            .getLogger(AdminController.class);

    @ModelAttribute("car")
    public Car createCarModel() {
        return new Car();
    }

    @ModelAttribute("filter")
    public CarFilter createCarFilterModel() {
        return new CarFilter();
    }

    @PostConstruct
    public void init() {
        carFilter = new CarFilter();
    }

    @RequestMapping(value = "/adminHome", method = RequestMethod.GET)
    public String getAdminHomePage(Model model) {
        return "adminHome";
    }

    @RequestMapping(value = "/filterAdminCars", method = RequestMethod.POST)
    public String filterAdminCars(@ModelAttribute("filter") CarFilter filter, Model model) {
        cars = adminRentACarService.searchACar(filter);
        carFilter = filter;
        carFilter.setCarAvailability(null);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "adminHome";
    }

    @RequestMapping(value = "/addCarAdmin", method = RequestMethod.GET)
    public String getAddCarFormPage(@ModelAttribute("car") Car car, Model model) {
        return "addCarAdmin";
    }

    private String cancelAction(Model model) {
        List<Car> cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "adminHome";
    }

    @RequestMapping(value = "/addCar.do", method = RequestMethod.POST)
    public String getAddCarFormAction(@ModelAttribute("car") Car car, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        carValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addCarAdmin";
        }
        car.setAvailability(CarAvailability.AVAILABLE);
        adminRentACarService.addACar(car);
        List<Car> cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "adminHome";
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.GET)
    public String getEditCarForm(@ModelAttribute("car") Car car, Model model) {
        return "editCarAdmin";
    }

    @RequestMapping(value = "/editCar.do", method = RequestMethod.POST)
    public String getEditCarFormAction(@ModelAttribute("car") Car car, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        editCarValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return "editCarAdmin";
        }
        carService.updateCar(car);
        List<Car> cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return "adminHome";
    }

    @RequestMapping(value = "/carListAdminAction", method = RequestMethod.POST)
    public String viewCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        return carAction(carWinCode, model, request);
    }

    private String carAction(String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        if (carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
        } else {
            List<Car> cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "adminHome";
        }

        if ("CANCEL BOOK".equals(action)) {
            adminRentACarService.cancelBookingByCar(car);
            List<Car> cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "adminHome";
        } else if ("CANCEL RENT".equals(action)) {
            adminRentACarService.cancelRentByCar(car);
            List<Car> cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "adminHome";
        } else if ("EDIT CAR".equals(action)) {
            model.addAttribute("car", car);
            return "editCarAdmin";
        } else if ("UNSUSPEND".equals(action)) {
            adminRentACarService.unsuspendACar(car);
            List<Car> cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "adminHome";
        } else if ("SUSPEND".equals(action)) {
            adminRentACarService.suspendACar(car);
            List<Car> cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return "adminHome";
        }
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        model.addAttribute("car", car);
        return "viewCarAdmin";
    }
}
