package com.rentacar.validator;

import com.rentacar.model.Person;
import com.rentacar.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrei.Plesca
 */
@Component
public class PersonValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String STRING_PATTERN = "[a-zA-Z]+";
    String DRIVING_LICENSE_PATTERN = "^[A-Z]{2}[0-9]{6}[A-Z]{1}";
    String DATE_PATTERN = "^\\d{2}-\\d{2}-\\d{4}$";

    @Autowired
    private PersonService personService;

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (person.getPassword().length() < 5 || person.getPassword().length() > 20) {
            errors.rejectValue("password", "Size.personForm.password");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkPassword", "NotEmpty");
        if (person.getCheckPassword().length() < 5 || person.getCheckPassword().length() > 20) {
            errors.rejectValue("checkPassword", "Size.personForm.checkPassword");
        } else if (!person.getCheckPassword().equals(person.getPassword())) {
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
            matcher = pattern.matcher(person.getDrivingLicense().getLicenseNumber().toString());
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
