package com.rentacar.dao;

import com.rentacar.model.Person;
import com.rentacar.model.enums.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Andrei.Plesca
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findUserByEmailAndUserPasswordAndUserRole(String email, String password, UserRole userRole);

    Person findByEmail(String email);

    Person findPersonByDrivingLicense_LicenseNumber(String licenseNumber);
}
