package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import com.rentacar.validator.LoginAdminValidator;
import com.rentacar.validator.LoginUserValidator;
import com.rentacar.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.rentacar.util.ModelAttributeConstants.*;
import static com.rentacar.util.PageActionsConstants.*;
import static com.rentacar.util.PageNavigationConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class PersonController {
    private final PersonService personService;
    private final CarService carService;
    private final PersonValidator personValidator;
    private final LoginUserValidator loginUserValidator;
    private final LoginAdminValidator loginAdminValidator;

    @Autowired
    public PersonController(PersonService personService, CarService carService, PersonValidator personValidator, LoginUserValidator loginUserValidator, LoginAdminValidator loginAdminValidator) {
        this.personService = personService;
        this.carService = carService;
        this.personValidator = personValidator;
        this.loginUserValidator = loginUserValidator;
        this.loginAdminValidator = loginAdminValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @ModelAttribute(PERSON_MODEL_ATTRIBUTE)
    public Person createEmployeeModel() {
        return new Person();
    }

    @ModelAttribute(CAR_MODEL_ATTRIBUTE)
    public Car createCarModel() {
        return new Car();
    }

    @ModelAttribute(FILTER_MODEL_ATTRIBUTE)
    public CarFilter createFilterModel() {
        return new CarFilter();
    }

    @ModelAttribute(ADMIN_MODEL_ATTRIBUTE)
    public Login createLoginModelForAdmin() {
        return new Login();
    }

    @ModelAttribute(LOGIN_MODEL_ATTRIBUTE)
    public Login createLoginModelForUser() {
        return new Login();
    }

    @RequestMapping(value = {USER_PAGE_LOGIN1_ACTION, USER_PAGE_LOGIN2_ACTION}, method = RequestMethod.GET)
    public String getLoginFormPage() {
        return USER_PAGE_LOGIN;
    }

    @RequestMapping(value = USER_PAGE_LOGIN_ACTION_ACTION, method = RequestMethod.POST)
    public String getLoginFormAction(@ModelAttribute(LOGIN_MODEL_ATTRIBUTE) Login login, BindingResult bindingResult, Model model, HttpSession session) {
        loginUserValidator.validate(login, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_PAGE_LOGIN;
        }
        Person person = personService.logIn(login);
        session.setAttribute(USER_MODEL_ATTRIBUTE, person);
        List<Car> cars = carService.showAllAvailableCars();
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = ADMIN_PAGE_LOGIN_VIEW_ACTION, method = RequestMethod.GET)
    public String getLoginAdminFormPage() {
        return ADMIN_PAGE_LOGIN;
    }

    @RequestMapping(value = ADMIN_PAGE_LOGIN_ACTION_ACTION, method = RequestMethod.POST)
    public String getLoginAdminFormAction(@ModelAttribute(ADMIN_MODEL_ATTRIBUTE) Login login, BindingResult bindingResult, Model model, HttpSession session) {
        loginAdminValidator.validate(login, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_LOGIN;
        }
        Person person = personService.adminLogIn(login);
        session.setAttribute(USER_MODEL_ATTRIBUTE, person);
        List<Car> cars = carService.showAllCars();
        model.addAttribute(CARS_MODEL_ATTRIBUTE, cars);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = USER_PAGE_REGISTER_VIEW_ACTION, method = RequestMethod.GET)
    public String getRegistrationFormPage() {
        return USER_PAGE_REGISTER;
    }

    @RequestMapping(value = USER_PAGE_REGISTER_ACTION_ACTION, method = RequestMethod.POST)
    public String getRegistrationFormAction(@ModelAttribute(PERSON_MODEL_ATTRIBUTE) Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_PAGE_REGISTER;
        }

        personService.savePerson(person);
        return USER_PAGE_LOGIN;
    }
}
