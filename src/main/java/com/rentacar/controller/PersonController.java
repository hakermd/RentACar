package com.rentacar.controller;

import com.rentacar.model.*;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import com.rentacar.validator.LoginAdminValidator;
import com.rentacar.validator.LoginUserValidator;
import com.rentacar.validator.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.rentacar.util.PageNavigationConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Controller
public class PersonController {
    private static final Logger logger = LoggerFactory
            .getLogger(PersonController.class);
    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private LoginUserValidator loginUserValidator;

    @Autowired
    private LoginAdminValidator loginAdminValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @ModelAttribute("person")
    public Person createEmployeeModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Person();
    }

    @ModelAttribute("car")
    public Car createCarModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Car();
    }

    @ModelAttribute("filter")
    public CarFilter createFilterModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new CarFilter();
    }

    @ModelAttribute("admin")
    public Login createLoginModelForAdmin() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Login();
    }

    @ModelAttribute("login")
    public Login createLoginModelForUser() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Login();
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String getLoginFormPage(Model model) {
        return USER_PAGE_LOGIN;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String getLoginFormAction(@ModelAttribute("login") Login login, BindingResult bindingResult, Model model, HttpSession session) {
        loginUserValidator.validate(login, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_PAGE_LOGIN;
        }
        Person person = personService.logIn(login);
        session.setAttribute("user", person);
        List<Car> cars = carService.showAllAvailableCars();
        model.addAttribute("cars", cars);
        return USER_PAGE_AVAILABLE_CARS;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String getLoginAdminFormPage(Model model) {
        return ADMIN_PAGE_LOGIN;
    }

    @RequestMapping(value = "/adminLogin.do", method = RequestMethod.POST)
    public String getLoginAdminFormAction(@ModelAttribute("admin") Login login, BindingResult bindingResult, Model model, HttpSession session) {
        loginAdminValidator.validate(login, bindingResult);

        if (bindingResult.hasErrors()) {
            return ADMIN_PAGE_LOGIN;
        }
        Person person = personService.adminLogIn(login);
        session.setAttribute("user", person);
        List<Car> cars = carService.showAllCars();
        model.addAttribute("cars", cars);
        return ADMIN_PAGE_HOME;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationFormPage(Model model) {
        return USER_PAGE_REGISTER;
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String getRegistrationFormAction(@ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_PAGE_REGISTER;
        }

        personService.savePerson(person);
        return USER_PAGE_LOGIN;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
