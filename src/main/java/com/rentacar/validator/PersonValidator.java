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

import static com.rentacar.util.PersonModelConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Component
public class PersonValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String DRIVING_LICENSE_PATTERN = "^[A-Z]{2}[0-9]{6}[A-Z]";
    private static final String NOT_EMPTY = "NotEmpty";
    private Pattern pattern;
    private Matcher matcher;

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_FIRST_NAME, NOT_EMPTY);
        if (person.getFirstName().length() < 3 || person.getFirstName().length() > 32) {
            errors.rejectValue(PERSON_FIRST_NAME, "Size.personForm.firstName");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_LAST_NAME, NOT_EMPTY);
        if (person.getLastName().length() < 3 || person.getLastName().length() > 32) {
            errors.rejectValue(PERSON_LAST_NAME, "Size.personForm.lastName");
        }
        validateEmail(person, errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_ADDRESS, NOT_EMPTY);
        if (person.getAddress().length() < 10 || person.getAddress().length() > 50) {
            errors.rejectValue(PERSON_ADDRESS, "Size.personForm.address");
        }
        validatePassword(person, errors);
        validateBirthDate(person, errors);
        validateDrivingLicense(person, errors);
    }

    private void validateBirthDate(Person person, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_DOB, NOT_EMPTY);
        if (person.getBirthDate() != null) {
            Date today = new Date();
            Date birthDate = person.getBirthDate();
            if (birthDate.after(today)) {
                errors.rejectValue(PERSON_DOB, "Incorrect.personForm.birthDateAfter");
            } else if (birthDate.equals(today)) {
                errors.rejectValue(PERSON_DOB, "Incorrect.personForm.birthDateEquals");
            }
        }
    }

    private void validatePassword(Person person, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_USER_PSW, NOT_EMPTY);
        if (person.getUserPassword().length() < 5 || person.getUserPassword().length() > 20) {
            errors.rejectValue(PERSON_USER_PSW, "Size.personForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_CHECK_PSW, NOT_EMPTY);
        if (person.getCheckPassword().length() < 5 || person.getCheckPassword().length() > 20) {
            errors.rejectValue(PERSON_CHECK_PSW, "Size.personForm.checkPassword");
        } else if (!person.getCheckPassword().equals(person.getUserPassword())) {
            errors.rejectValue(PERSON_CHECK_PSW, "NotMatch.personForm.checkPassword");
        }
    }

    private void validateEmail(Person person, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_EMAIL, NOT_EMPTY);

        if (person.getEmail() != null && !person.getEmail().isEmpty()) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(person.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue(PERSON_EMAIL, "Incorrect.personForm.email");
            }
        }
        if (personService.findByEmail(person.getEmail()) != null) {
            errors.rejectValue(PERSON_EMAIL, "Duplicate.personForm.email");
        }
    }

    private void validateDrivingLicense(Person person, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_DRIVING_LICENSE_NUMBER, NOT_EMPTY);
        if ((person.getDrivingLicense().getLicenseNumber() != null) && !person.getDrivingLicense().getLicenseNumber().isEmpty()) {
            pattern = Pattern.compile(DRIVING_LICENSE_PATTERN);
            matcher = pattern.matcher(person.getDrivingLicense().getLicenseNumber());
            if (!matcher.matches()) {
                errors.rejectValue(PERSON_DRIVING_LICENSE_NUMBER, "Incorrect.personForm.licenseNumber");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_DRIVING_LICENSE_OBTAIN_DATE, NOT_EMPTY);
        if ((person.getDrivingLicense().getObtainingDate() != null)) {
            Date today = new Date();

            Date licenseObtainingDate = person.getDrivingLicense().getObtainingDate();

            if (licenseObtainingDate.after(today)) {
                errors.rejectValue(PERSON_DRIVING_LICENSE_OBTAIN_DATE, "Incorrect.personForm.licenseObtainingDateAfter");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERSON_DRIVING_LICENSE_EXPIRE_DATE, NOT_EMPTY);
        if ((person.getDrivingLicense().getExpiringDate() != null)) {
            Date today = new Date();
            Date licenseObtainingDate = person.getDrivingLicense().getObtainingDate();
            Date licenseExpiringDate = person.getDrivingLicense().getExpiringDate();

            if (licenseExpiringDate.before(today)) {
                errors.rejectValue(PERSON_DRIVING_LICENSE_EXPIRE_DATE, "Incorrect.personForm.licenseExpiringDateBefore");
            } else if (licenseExpiringDate.equals(licenseObtainingDate)) {
                errors.rejectValue(PERSON_DRIVING_LICENSE_EXPIRE_DATE, "Incorrect.personForm.licenseExpiringDateEquals");
            }
        }
    }
}
