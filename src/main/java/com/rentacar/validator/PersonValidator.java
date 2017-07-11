package com.rentacar.validator;

import com.rentacar.model.Person;
import com.rentacar.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrei.Plesca
 */
@Component
public class PersonValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String DRIVING_LICENSE_PATTERN = "^[A-Z]{2}[0-9]{6}[A-Z]";

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (person.getFirstName().length() < 3 || person.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.personForm.firstName");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (person.getLastName().length() < 3 || person.getLastName().length() > 32) {
            errors.rejectValue("lastName", "Size.personForm.lastName");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        Pattern pattern;
        Matcher matcher;
        if (person.getEmail() != null && !person.getEmail().isEmpty()) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(person.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue("email", "Incorrect.personForm.email");
            }
        }
        if (personService.findByEmail(person.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.personForm.email");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
        if (person.getAddress().length() < 10 || person.getAddress().length() > 50) {
            errors.rejectValue("address", "Size.personForm.address");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "NotEmpty");
        if (person.getUserPassword().length() < 5 || person.getUserPassword().length() > 20) {
            errors.rejectValue("userPassword", "Size.personForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkPassword", "NotEmpty");
        if (person.getCheckPassword().length() < 5 || person.getCheckPassword().length() > 20) {
            errors.rejectValue("checkPassword", "Size.personForm.checkPassword");
        } else if (!person.getCheckPassword().equals(person.getUserPassword())) {
            errors.rejectValue("checkPassword", "NotMatch.personForm.checkPassword");
        }


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "NotEmpty");
        if (person.getBirthDate() != null) {
            Date today = new Date();
            Date birthDate = person.getBirthDate();
            if (birthDate.after(today)) {
                errors.rejectValue("birthDate", "Incorrect.personForm.birthDateAfter");
            } else if (birthDate.equals(today)) {
                errors.rejectValue("birthDate", "Incorrect.personForm.birthDateEquals");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drivingLicense.licenseNumber", "NotEmpty");
        if ((person.getDrivingLicense().getLicenseNumber() != null) && !person.getDrivingLicense().getLicenseNumber().isEmpty()) {
            pattern = Pattern.compile(DRIVING_LICENSE_PATTERN);
            matcher = pattern.matcher(person.getDrivingLicense().getLicenseNumber());
            if (!matcher.matches()) {
                errors.rejectValue("drivingLicense.licenseNumber", "Incorrect.personForm.licenseNumber");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drivingLicense.obtainingDate", "NotEmpty");
        if ((person.getDrivingLicense().getObtainingDate() != null)) {
            Date today = new Date();

            Date licenseObtainingDate = person.getDrivingLicense().getObtainingDate();

            if (licenseObtainingDate.after(today)) {
                errors.rejectValue("drivingLicense.obtainingDate", "Incorrect.personForm.licenseObtainingDateAfter");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "drivingLicense.expiringDate", "NotEmpty");
        if ((person.getDrivingLicense().getExpiringDate() != null)) {
            Date today = new Date();
            Date licenseObtainingDate = person.getDrivingLicense().getObtainingDate();
            Date licenseExpiringDate = person.getDrivingLicense().getExpiringDate();

            if (licenseExpiringDate.before(today)) {
                errors.rejectValue("drivingLicense.expiringDate", "Incorrect.personForm.licenseExpiringDateBefore");
            } else if (licenseExpiringDate.equals(licenseObtainingDate)) {
                errors.rejectValue("drivingLicense.expiringDate", "Incorrect.personForm.licenseExpiringDateEquals");
            }
        }

    }
}
