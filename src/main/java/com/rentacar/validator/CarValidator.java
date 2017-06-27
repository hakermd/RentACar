package com.rentacar.validator;

import com.rentacar.model.Car;
import com.rentacar.services.CarService;
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
public class CarValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    String NO_DIGITS_PATTERN = "[a-zA-Z]+";
    String STRING_PATTERN = "[a-zA-Z0-9 ]+";
    String WIN_CODE_PATTERN = "[a-zA-Z0-9]{17}";
    String YEAR_PATTERN = "^\\d{4}$";

    @Autowired
    private CarService carService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "winCode", "NotEmpty");
        if (car.getWinCode() != null && !car.getWinCode().isEmpty()) {
            if (car.getWinCode().length() != 17) {
                errors.rejectValue("winCode", "Size.carForm.winCode");
            } else {
                pattern = Pattern.compile(WIN_CODE_PATTERN);
                matcher = pattern.matcher(car.getWinCode());
                if (!matcher.matches()) {
                    errors.rejectValue("winCode", "Incorrect.carForm.winCode");
                }
            }
            if (carService.findCarByWinCode(car.getWinCode()) != null) {
                errors.rejectValue("winCode", "Duplicate.carForm.winCode");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manufacturer", "NotEmpty");
        if (car.getManufacturer() != null && !car.getManufacturer().isEmpty()) {
            if (car.getManufacturer().length() < 3 && car.getManufacturer().length() > 15) {
                errors.rejectValue("manufacturer", "Size.carForm.manufacturer");
            } else {
                pattern = Pattern.compile(NO_DIGITS_PATTERN);
                matcher = pattern.matcher(car.getManufacturer());
                if (!matcher.matches()) {
                    errors.rejectValue("manufacturer", "Incorrect.carForm.manufacturer");
                }
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "NotEmpty");
        if (car.getModel() != null && !car.getModel().isEmpty()) {
            if (car.getModel().length() < 2 && car.getModel().length() > 15) {
                errors.rejectValue("model", "Size.carForm.model");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getModel());
                if (!matcher.matches()) {
                    errors.rejectValue("model", "Incorrect.carForm.model");
                }
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registrationNumber", "NotEmpty");
        if (car.getRegistrationNumber() != null && !car.getRegistrationNumber().isEmpty()) {
            if (car.getRegistrationNumber().length() < 4 && car.getRegistrationNumber().length() > 10) {
                errors.rejectValue("registrationNumber", "Size.carForm.registrationNumber");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getRegistrationNumber());
                if (!matcher.matches()) {
                    errors.rejectValue("registrationNumber", "Incorrect.carForm.registrationNumber");
                }
            }
            if (carService.findCarByRegistrationNumber(car.getRegistrationNumber()) != null) {
                errors.rejectValue("registrationNumber", "Duplicate.carForm.registrationNumber");
            }
        }
        if (car.getEngineVolume() < 700 || car.getEngineVolume() > 10000) {
            errors.rejectValue("engineVolume", "Size.carForm.engineVolume");
        }
        if (car.getCarPrice() < 10.0 || car.getCarPrice() > 300.0) {
            errors.rejectValue("carPrice", "Size.carForm.carPrice");
        }
    }
}
