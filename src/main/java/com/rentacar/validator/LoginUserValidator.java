package com.rentacar.validator;

import com.rentacar.model.Login;
import com.rentacar.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.rentacar.util.PersonModelConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Component
public class LoginUserValidator implements Validator {

    private final PersonService personService;
    private final LoginValidator loginValidator;

    @Autowired
    public LoginUserValidator(PersonService personService, LoginValidator loginValidator) {
        this.personService = personService;
        this.loginValidator = loginValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Login.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login = (Login) o;
        loginValidator.validate(o, errors);
        if (personService.logIn(login) == null) {
            errors.rejectValue(PERSON_USER_PSW, "Error.loginForm.login");
        }
    }
}
