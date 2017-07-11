package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.validator.CarValidator;
import com.rentacar.validator.EditCarValidator;
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

import static com.rentacar.util.PageNavigationConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class AdminController {
    private final AdminRentACarService adminRentACarService;
    private final CarService carService;
    private final CarValidator carValidator;
    private final EditCarValidator editCarValidator;

    private List<Car> cars;
    private CarFilter carFilter;

    @Autowired
    public AdminController(AdminRentACarService adminRentACarService, CarService carService, CarValidator carValidator, EditCarValidator editCarValidator) {
        this.adminRentACarService = adminRentACarService;
        this.carService = carService;
        this.carValidator = carValidator;
        this.editCarValidator = editCarValidator;
    }

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
    public String getAdminHomePage() {
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/filterAdminCars", method = RequestMethod.POST)
    public String filterAdminCars(@ModelAttribute("filter") CarFilter filter, Model model) {
        cars = adminRentACarService.searchACar(filter);
        carFilter = filter;
        carFilter.setCarAvailability(null);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/addCarAdmin", method = RequestMethod.GET)
    public String getAddCarFormPage(@ModelAttribute("car") Car car) {
        return ADMIN_PAGE_ADD_CAR;
    }

    private String cancelAction(Model model) {
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/addCar.do", method = RequestMethod.POST)
    public String getAddCarFormAction(@ModelAttribute("car") Car car, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        carValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_ADD_CAR;
        }
        car.setAvailability(CarAvailability.AVAILABLE);
        adminRentACarService.addACar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.GET)
    public String getEditCarForm(@ModelAttribute("car") Car car) {
        return ADMIN_PAGE_EDIT_CAR;
    }

    @RequestMapping(value = "/editCar.do", method = RequestMethod.POST)
    public String getEditCarFormAction(@ModelAttribute("car") Car car, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        editCarValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_EDIT_CAR;
        }
        carService.updateCar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute("cars", cars);
        model.addAttribute("filter", carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/carListAdminAction", method = RequestMethod.POST)
    public String viewCarAction(@ModelAttribute("carWinCode") String carWinCode, Model model, HttpServletRequest request) {
        return carAction(carWinCode, model, request);
    }

    private String carAction(String carWinCode, Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        Car car;
        if (carWinCode != null) {
            car = carService.findCarByWinCode(carWinCode);
        } else {
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return ADMIN_PAGE_HOME;
        }

        if ("CANCEL BOOK".equals(action)) {
            adminRentACarService.cancelBookingByCar(car);
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return ADMIN_PAGE_HOME;
        } else if ("CANCEL RENT".equals(action)) {
            adminRentACarService.cancelRentByCar(car);
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return ADMIN_PAGE_HOME;
        } else if ("EDIT CAR".equals(action)) {
            model.addAttribute("car", car);
            return ADMIN_PAGE_EDIT_CAR;
        } else if ("UNSUSPEND".equals(action)) {
            adminRentACarService.unsuspendACar(car);
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return ADMIN_PAGE_HOME;
        } else if ("SUSPEND".equals(action)) {
            adminRentACarService.suspendACar(car);
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute("cars", cars);
            model.addAttribute("filter", carFilter);
            return ADMIN_PAGE_HOME;
        }
        if ("CANCEL".equals(action)) {
            return cancelAction(model);
        }
        model.addAttribute("car", car);
        return ADMIN_PAGE_VIEW_CAR;
    }
}
