package com.rentacar.dao;

import com.rentacar.model.*;
import com.rentacar.testutils.TestDataUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class BookingDaoTest {

    private Session session;
    private BookingDao bookingDao;
    private InsuranceDao insuranceDao;
    private CarDao carDao;
    private PersonDao personDao;

    private Insurance insurance;
    private Person person;
    private Car car;

    private Booking booking;
    private Booking currentBooking;

    @Autowired
    private void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Autowired
    private void setInsuranceDao(InsuranceDao insuranceDao) {
        this.insuranceDao = insuranceDao;
    }

    @Autowired
    private void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Autowired
    private void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    @Before
    public void setUp() throws ParseException {
        session = insuranceDao.getSessionFactory().getCurrentSession();
        insurance = TestDataUtil.getMockInsurance();
        car = TestDataUtil.getMockCar();
        person = TestDataUtil.getMockPerson();
        booking = TestDataUtil.getMockBooking();

        personDao.save(person);
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        insurance.setPerson(person);
        insurance.setCar(car);
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        booking.setPerson(person);
        booking.setCar(car);
        booking.setInsurance(insurance);
        booking.setCost(car.getCarPrice() + insurance.getCost());
    }

    @After
    public void cleanUp() throws ParseException {
        if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
        bookingDao.delete(currentBooking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        insuranceDao.delete(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        carDao.delete(car);
        personDao.delete(person);
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void findBookingById() throws Exception {
        session.getTransaction().begin();
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }

    @Test
    public void findAllBookings() throws Exception {
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
        List<Booking> bookingList = bookingDao.findAll();
        assertNotNull(bookingList);
        assertTrue(bookingList.size() >= 1);
    }

    @Test
    public void addBooking() throws Exception {
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }

    @Test
    public void updateBooking() throws Exception {
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
        currentBooking.setCost(150);
        bookingDao.update(currentBooking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }

    @Test
    public void deleteBooking() throws Exception {
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
        bookingDao.delete(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
    }

    @Test
    public void deleteBookingById() throws Exception {
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
        bookingDao.deleteById(booking.getBookingId());
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNull(currentBooking);
    }

    @Test
    public void findBookingByCode() throws Exception {
        session.getTransaction().begin();
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.findOne(booking.getBookingId());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
        currentBooking = bookingDao.findBookingByCode(booking.getBookingCode());
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }

    @Test
    public void getBookingByCar() throws Exception {
        session.getTransaction().begin();
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.getBookingByCar(car);
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }

    @Test
    public void getBookingByPerson() throws Exception {
        session.getTransaction().begin();
        bookingDao.save(booking);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentBooking = bookingDao.getBookingByPerson(person);
        assertNotNull(currentBooking);
        assertEquals(booking, currentBooking);
    }
}