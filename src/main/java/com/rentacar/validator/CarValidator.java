package com.rentacar.validator;

import com.rentacar.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rentacar.util.CarModelConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Component
public class CarValidator implements Validator {
    private static final String NO_DIGITS_PATTERN = "[a-zA-Z ]+";
    private static final String STRING_PATTERN = "[a-zA-Z0-9 ]+";
    private static final String WIN_CODE_PATTERN = "[a-zA-Z0-9]{17}";
    private static final String NOT_EMPTY = "NotEmpty";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;

        validateWinCode(car, errors);
        validateManufacturer(car, errors);
        validateModel(car, errors);
        validateRegistrationNumber(car, errors);

        if (car.getEngineVolume() < 700 || car.getEngineVolume() > 10000) {
            errors.rejectValue(CAR_ENGINE_VOLUME, "Size.carForm.engineVolume");
        }
        if (car.getCarPrice() < 10.0 || car.getCarPrice() > 300.0) {
            errors.rejectValue(CAR_PRICE, "Size.carForm.carPrice");
        }
    }

    private void validateModel(Car car, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CAR_MODEL, NOT_EMPTY);
        if (car.getModel() != null && !car.getModel().isEmpty()) {
            if (car.getModel().length() < 2 && car.getModel().length() > 15) {
                errors.rejectValue(CAR_MODEL, "Size.carForm.model");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getModel());
                if (!matcher.matches()) {
                    errors.rejectValue(CAR_MODEL, "Incorrect.carForm.model");
                }
            }
        }
    }

    private void validateManufacturer(Car car, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CAR_MANUFACTURER, NOT_EMPTY);
        if (car.getManufacturer() != null && !car.getManufacturer().isEmpty()) {
            if (car.getManufacturer().length() < 3 && car.getManufacturer().length() > 15) {
                errors.rejectValue(CAR_MANUFACTURER, "Size.carForm.manufacturer");
            } else {
                pattern = Pattern.compile(NO_DIGITS_PATTERN);
                matcher = pattern.matcher(car.getManufacturer());
                if (!matcher.matches()) {
                    errors.rejectValue(CAR_MANUFACTURER, "Incorrect.carForm.manufacturer");
                }
            }
        }
    }

    private void validateWinCode(Car car, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CAR_WIN_CODE, NOT_EMPTY);
        if (car.getWinCode() != null && !car.getWinCode().isEmpty()) {
            if (car.getWinCode().length() != 17) {
                errors.rejectValue(CAR_WIN_CODE, "Size.carForm.winCode");
            } else {
                pattern = Pattern.compile(WIN_CODE_PATTERN);
                matcher = pattern.matcher(car.getWinCode());
                if (!matcher.matches()) {
                    errors.rejectValue(CAR_WIN_CODE, "Incorrect.carForm.winCode");
                }
            }
        }
    }

    private void validateRegistrationNumber(Car car, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CAR_REGISTRATION_NUMBER, NOT_EMPTY);
        if (car.getRegistrationNumber() != null && !car.getRegistrationNumber().isEmpty()) {
            if (car.getRegistrationNumber().length() < 4 && car.getRegistrationNumber().length() > 10) {
                errors.rejectValue(CAR_REGISTRATION_NUMBER, "Size.carForm.registrationNumber");
            } else {
                pattern = Pattern.compile(STRING_PATTERN);
                matcher = pattern.matcher(car.getRegistrationNumber());
                if (!matcher.matches()) {
                    errors.rejectValue(CAR_REGISTRATION_NUMBER, "Incorrect.carForm.registrationNumber");
                }
            }
        }
    }
}
