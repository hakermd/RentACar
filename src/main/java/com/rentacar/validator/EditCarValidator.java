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
public class EditCarValidator implements Validator {
    private static final String NO_DIGITS_PATTERN = "[a-zA-Z]+";
    private static final String STRING_PATTERN = "[a-zA-Z0-9 ]+";
    private static final String WIN_CODE_PATTERN = "[a-zA-Z0-9]{17}";

    private final CarService carService;

    @Autowired
    public EditCarValidator(CarService carService) {
        this.carService = carService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "winCode", "NotEmpty");
        Pattern pattern;
        Matcher matcher;
        if (car.getWinCode() != null && !car.getWinCode().isEmpty()) {
            if (car.getWinCode().length() != 17) {
                errors.rejectValue("winCode", "Size.carEditForm.winCode");
            } else {
                pattern = Pattern.compile(WIN_CODE_PATTERN);
                matcher = pattern.matcher(car.getWinCode());
                if (!matcher.matches()) {
                    errors.rejectValue("winCode", "Incorrect.carEditForm.winCode");
                }
            }
            Car existingCar = carService.findCarByWinCode(car.getWinCode());
            if (existingCar != null && car.getCarId() != existingCar.getCarId()) {
                errors.rejectValue("winCode", "Duplicate.carEditForm.winCode");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "manufacturer", "NotEmpty");
        if (car.getManufacturer() != null && !car.getManufacturer().isEmpty()) {
            if (car.getManufacturer().length() < 3 && car.getManufacturer().length() > 15) {
                errors.rejectValue("manufacturer", "Size.carEditForm.manufacturer");
            } else {
                pattern = Pattern.compile(NO_DIGITS_PATTERN);
                matcher = pattern.matcher(car.getManufacturer());
                if (!matcher.matches()) {
                    errors.rejectValue("manufacturer", "Incorrect.carEditForm.manufacturer");
                }
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "NotEmpty");
        if (car.getModel() != null && !car.getModel().isEmpty()) {
            if (car.getModel().length() < 2 && car.getModel().length() > 15) {
                errors.rejectValue("model", "Size.carEditForm.model");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getModel());
                if (!matcher.matches()) {
                    errors.rejectValue("model", "Incorrect.carEditForm.model");
                }
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registrationNumber", "NotEmpty");
        if (car.getRegistrationNumber() != null && !car.getRegistrationNumber().isEmpty()) {
            if (car.getRegistrationNumber().length() < 4 && car.getRegistrationNumber().length() > 10) {
                errors.rejectValue("registrationNumber", "Size.carEditForm.registrationNumber");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getRegistrationNumber());
                if (!matcher.matches()) {
                    errors.rejectValue("registrationNumber", "Incorrect.carEditForm.registrationNumber");
                }
            }
            Car existingCar = carService.findCarByRegistrationNumber(car.getRegistrationNumber());
            if (existingCar != null && car.getCarId() != existingCar.getCarId()) {
                errors.rejectValue("registrationNumber", "Duplicate.carEditForm.registrationNumber");
            }
        }
        if (car.getEngineVolume() < 700 || car.getEngineVolume() > 10000) {
            errors.rejectValue("engineVolume", "Size.carEditForm.engineVolume");
        }
        if (car.getCarPrice() < 10.0 || car.getCarPrice() > 300.0) {
            errors.rejectValue("carPrice", "Size.carEditForm.carPrice");
        }
    }
}
