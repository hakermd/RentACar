package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.validator.AddCarValidator;
import com.rentacar.validator.EditCarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.rentacar.util.ModelAttributeConstants.*;
import static com.rentacar.util.PageActionsConstants.*;
import static com.rentacar.util.PageNavigationConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class AdminController {
    private final AdminRentACarService adminRentACarService;
    private final CarService carService;
    private final AddCarValidator addCarValidator;
    private final EditCarValidator editCarValidator;

    private List<Car> cars;
    private CarFilter carFilter;
    private Car car;

    @Autowired
    public AdminController(AdminRentACarService adminRentACarService, CarService carService, AddCarValidator addCarValidator, EditCarValidator editCarValidator) {
        this.adminRentACarService = adminRentACarService;
        this.carService = carService;
        this.addCarValidator = addCarValidator;
        this.editCarValidator = editCarValidator;
    }

    @ModelAttribute(CAR_MODEL_ATTRIBUTE)
    public Car createCarModel() {
        return new Car();
    }

    @ModelAttribute(FILTER_MODEL_ATTRIBUTE)
    public CarFilter createCarFilterModel() {
        return new CarFilter();
    }

    @PostConstruct
    public void init() {
        carFilter = new CarFilter();
    }

    @RequestMapping(value = ADMIN_PAGE_HOME_ACTION, method = RequestMethod.GET)
    public String getAdminHomePage() {
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_FILTER_CARS_ACTION, method = RequestMethod.POST)
    public String filterAdminCars(@ModelAttribute(FILTER_MODEL_ATTRIBUTE) CarFilter filter, Model model) {
        cars = adminRentACarService.searchACar(filter);
        carFilter = filter;
        carFilter.setCarAvailability(null);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_ADD_CAR_VIEW_ACTION, method = RequestMethod.GET)
    public String getAddCarFormPage(@ModelAttribute(CAR_MODEL_ATTRIBUTE) Car car) {
        return ADMIN_PAGE_ADD_CAR;
    }

    @RequestMapping(value = ADMIN_PAGE_ADD_CAR_ACTION, params = "addCar", method = RequestMethod.POST)
    public String getAddCarFormAction(@ModelAttribute(CAR_MODEL_ATTRIBUTE) Car car, BindingResult bindingResult, Model model) {
        addCarValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_ADD_CAR;
        }
        car.setAvailability(CarAvailability.AVAILABLE);
        adminRentACarService.addACar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_VIEW_CAR_VIEW_ACTION, method = RequestMethod.GET)
    public String getViewCarForm(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        return ADMIN_PAGE_VIEW_CAR;
    }

    @RequestMapping(value = ADMIN_PAGE_EDIT_CAR_ACTION, params = "edit", method = RequestMethod.POST)
    public String getEditCarFormAction(@ModelAttribute(CAR_MODEL_ATTRIBUTE) Car car, BindingResult bindingResult, Model model) {
        editCarValidator.validate(car, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_EDIT_CAR;
        }
        carService.updateCar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = {ADMIN_PAGE_ACTION_FORM_ACTION, ADMIN_PAGE_ADD_CAR_ACTION, ADMIN_PAGE_EDIT_CAR_ACTION}, params = "cancel", method = RequestMethod.POST)
    public String cancelAction(Model model) {
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_ACTION_FORM_ACTION, params = "suspend", method = RequestMethod.POST)
    public String getSuspendCarAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        adminRentACarService.suspendACar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_ACTION_FORM_ACTION, params = "unSuspend", method = RequestMethod.POST)
    public String getUnSuspendCarAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        adminRentACarService.unsuspendACar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_ACTION_FORM_ACTION, params = "editCar", method = RequestMethod.POST)
    public String getEditCarAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        return ADMIN_PAGE_EDIT_CAR;
    }

    @RequestMapping(value = ADMIN_PAGE_ACTION_FORM_ACTION, params = "cancelBooking", method = RequestMethod.POST)
    public String geCancelBookingAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        adminRentACarService.cancelBookingByCar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = ADMIN_PAGE_ACTION_FORM_ACTION, params = "cancelRent", method = RequestMethod.POST)
    public String geCancelRentAction(@ModelAttribute(CAR_WIN_CODE_MODEL_ATTRIBUTE) String carWinCode, Model model) {
        checkWinCode(carWinCode, model);
        adminRentACarService.cancelRentByCar(car);
        cars = adminRentACarService.searchACar(carFilter);
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        return ADMIN_PAGE_HOME;
    }

    private void checkWinCode(String winCode, Model model) {
        if (winCode != null) {
            car = carService.findCarByWinCode(winCode);
            model.addAttribute(CAR_MODEL_ATTRIBUTE, car);
        } else {
            cars = adminRentACarService.searchACar(carFilter);
            model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
            model.addAttribute(FILTER_MODEL_ATTRIBUTE, carFilter);
        }
    }
}
