package com.rentacar.validator;

import com.rentacar.model.Car;
import com.rentacar.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.rentacar.util.CarModelConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Component
public class EditCarValidator implements Validator {
    private final CarService carService;
    private final CarValidator carValidator;

    @Autowired
    public EditCarValidator(CarService carService, CarValidator carValidator) {
        this.carService = carService;
        this.carValidator = carValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;
        carValidator.validate(o, errors);

        if (car.getWinCode() != null && !car.getWinCode().isEmpty()) {
            Car existingCar = carService.findCarByWinCode(car.getWinCode());
            if (existingCar != null && car.getCarId() != existingCar.getCarId()) {
                errors.rejectValue(CAR_WIN_CODE, "Duplicate.carEditForm.winCode");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registrationNumber", "NotEmpty");
        if (car.getRegistrationNumber() != null && !car.getRegistrationNumber().isEmpty()) {
            Car existingCar = carService.findCarByRegistrationNumber(car.getRegistrationNumber());
            if (existingCar != null && car.getCarId() != existingCar.getCarId()) {
                errors.rejectValue(CAR_REGISTRATION_NUMBER, "Duplicate.carEditForm.registrationNumber");
            }
        }
    }
}
