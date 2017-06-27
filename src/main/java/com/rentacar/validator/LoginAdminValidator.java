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
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Login.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login = (Login) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (login.getEmail() != null && !login.getEmail().isEmpty()) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(login.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue("email", "Incorrect.loginAdminForm.email");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (login.getPassword().length() < 5 || login.getPassword().length() > 20) {
            errors.rejectValue("password", "Size.loginAdminForm.password");
        } else if (personService.adminLogIn(login) == null) {
            errors.rejectValue("password", "Error.loginAdminForm.login");
        }
//
//        if (login.getPassword() != null && login.getPassword() != null) {
//            if (!personService.findByEmail(login.getEmail()).getUserRole().equals(UserRole.ADMIN)) {
//                errors.rejectValue("password", "Rights.loginAdminForm.login");
//            }
//        }
    }
}
