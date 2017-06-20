package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @ModelAttribute("person")
    public Person createEmployeeModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Person();
    }

    @ModelAttribute("filter")
    public CarFilter createFilterModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new CarFilter();
    }


    @ModelAttribute("login")
    public Login createLoginModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Login();
    }

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String getLoginFormPage(Model model) {
        return "login";
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String getLoginFormAction(@ModelAttribute("login") Login login, Model model, HttpSession session) {
        if (isNullOrEmpty(login.getEmail()) || isNullOrEmpty(login.getPassword())) {
            return "login";
        } else {
            Person person = personService.logIn(login);
            if (person != null && person.getPersonId() != null) {
                session.setAttribute("user", person);
                List<Car> cars = carService.showAllAvailableCars();
                model.addAttribute("cars", cars);
                return "availableCars";
            } else return "login";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationFormPage(Model model) {
        return "register";
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String getRegistrationFormAction(@ModelAttribute("person") Person person, ModelMap model) {
        if (person.getFirstName() != null) {
            personService.savePerson(person);
            return "login";
        }
        return "register";
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
