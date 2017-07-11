package com.rentacar.validator;

import com.rentacar.model.Login;
import com.rentacar.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrei.Plesca
 */
@Component
public class LoginAdminValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final PersonService personService;

    @Autowired
    public LoginAdminValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Login.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login = (Login) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (login.getEmail() != null && !login.getEmail().isEmpty()) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(login.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue("email", "Incorrect.loginAdminForm.email");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "NotEmpty");
        if (login.getUserPassword().length() < 5 || login.getUserPassword().length() > 20) {
            errors.rejectValue("userPassword", "Size.loginAdminForm.password");
        } else if (personService.adminLogIn(login) == null) {
            errors.rejectValue("userPassword", "Error.loginAdminForm.login");
        }
    }
}
