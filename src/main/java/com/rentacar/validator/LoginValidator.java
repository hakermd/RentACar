package com.rentacar.validator;

import com.rentacar.model.Login;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rentacar.util.PersonModelConstants.PERSON_EMAIL;
import static com.rentacar.util.PersonModelConstants.PERSON_USER_PSW;

/**
 * Created by Andrei.Plesca
 */
@Component
public class LoginValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean supports(Class<?> aClass) {
        return Login.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login login = (Login) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_EMAIL, "NotEmpty");
        if (login.getEmail() != null && !login.getEmail().isEmpty()) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(login.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue(PERSON_EMAIL, "Incorrect.loginForm.email");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_USER_PSW, "NotEmpty");
        if (login.getUserPassword().length() < 5 || login.getUserPassword().length() > 20) {
            errors.rejectValue(PERSON_USER_PSW, "Size.loginForm.password");
        }
    }
}
