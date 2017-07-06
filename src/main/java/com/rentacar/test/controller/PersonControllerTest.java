package com.rentacar.test.controller;

import com.rentacar.controller.PersonController;
import com.rentacar.model.Car;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import com.rentacar.test.testutils.TestDataUtil;
import com.rentacar.validator.LoginAdminValidator;
import com.rentacar.validator.LoginUserValidator;
import com.rentacar.validator.PersonValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.View;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Andrei.Plesca
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "/application-context.xml")
public class PersonControllerTest {
    private Person person;
    @InjectMocks
    private PersonController controller;
    @Mock
    private PersonService personService;
    @Mock
    private CarService carService;
    @Mock
    private LoginUserValidator loginUserValidator;
    @Mock
    private LoginAdminValidator loginAdminValidator;
    @Mock
    private PersonValidator personValidator;
    @Mock
    private View mockView;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(mockView)
                .build();
        person = TestDataUtil.getMockPerson();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLoginFormPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testGetLoginFormAction() throws Exception {
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setPassword(person.getPassword());

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(loginUserValidator).validate(login, bindingResult);

        when(personService.logIn(login)).thenReturn(person);

        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);

        mockMvc.perform(post("/login.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        "email", login.getEmail(),
                        "password", login.getPassword()
                )))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"));
    }

    @Test
    public void testGetLoginAdminFormPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminLogin"));
    }

    @Test
    public void testGetLoginAdminFormAction() throws Exception {
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setPassword(person.getPassword());

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(loginAdminValidator).validate(login, bindingResult);

        when(personService.adminLogIn(login)).thenReturn(person);

        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);

        mockMvc.perform(post("/adminLogin.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        "email", login.getEmail(),
                        "password", login.getPassword())))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testGetRegisterFormPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testGetRegistrationFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(personValidator).validate(person, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(personService).savePerson(person);

        mockMvc.perform(post("/register.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        "firstName", person.getFirstName(),
                        "lastName", person.getLastName(),
                        "email", person.getEmail(),
                        "address", person.getAddress(),
                        "password", person.getPassword(),
                        "checkPassword", person.getCheckPassword(),
                        "birthDate", "1988-01-01T00:00:00.000Z",
                        "gender", person.getGender().getValue(),
                        "drivingLicense.licenseNumber", person.getDrivingLicense().getLicenseNumber(),
                        "drivingLicense.obtainingDate", "2012-01-01T00:00:00.000Z",
                        "drivingLicense.expiringDate", "2022-01-01T00:00:00.000Z")))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}