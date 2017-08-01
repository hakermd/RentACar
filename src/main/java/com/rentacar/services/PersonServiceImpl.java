package com.rentacar.services;

import com.rentacar.dao.PersonRepository;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.model.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    @Override
    public Person logIn(Login login) {
        return personRepository.findUserByEmailAndUserPasswordAndUserRole(login.getEmail(), login.getUserPassword(), UserRole.USER);
    }

    @Override
    public Person adminLogIn(Login login) {
        return personRepository.findUserByEmailAndUserPasswordAndUserRole(login.getEmail(), login.getUserPassword(), UserRole.ADMIN);
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public Person findByLicenseNumber(String licenseNumber) {
        return personRepository.findPersonByDrivingLicense_LicenseNumber(licenseNumber);
    }

    @Override
    public Person findById(long id) {
        return personRepository.findOne(id);
    }
}