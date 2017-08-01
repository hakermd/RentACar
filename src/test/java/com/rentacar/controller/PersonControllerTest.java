package com.rentacar.controller;

import com.rentacar.config.TestWebConfig;
import com.rentacar.model.Car;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import com.rentacar.testutils.TestDataUtil;
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

import static com.rentacar.util.PageActionsConstants.*;
import static com.rentacar.util.PageNavigationConstants.*;
import static com.rentacar.util.PersonModelConstants.*;
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
@ContextConfiguration(classes = TestWebConfig.class)
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
        mockMvc.perform(get(USER_PAGE_LOGIN1_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_LOGIN));
    }

    @Test
    public void testGetLoginFormAction() throws Exception {
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setUserPassword(person.getUserPassword());

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(loginUserValidator).validate(login, bindingResult);

        when(personService.logIn(login)).thenReturn(person);

        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);

        mockMvc.perform(post(USER_PAGE_LOGIN_ACTION_ACTION)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        PERSON_EMAIL, login.getEmail(),
                        PERSON_USER_PSW, login.getUserPassword()
                )))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_AVAILABLE_CARS));
    }

    @Test
    public void testGetLoginAdminFormPage() throws Exception {
        mockMvc.perform(get(ADMIN_PAGE_LOGIN_VIEW_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_LOGIN));
    }

    @Test
    public void testGetLoginAdminFormAction() throws Exception {
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setUserPassword(person.getUserPassword());

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(loginAdminValidator).validate(login, bindingResult);

        when(personService.adminLogIn(login)).thenReturn(person);

        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);

        mockMvc.perform(post(ADMIN_PAGE_LOGIN_ACTION_ACTION)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        PERSON_EMAIL, login.getEmail(),
                        PERSON_USER_PSW, login.getUserPassword())))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testGetRegisterFormPage() throws Exception {
        mockMvc.perform(get(USER_PAGE_REGISTER_VIEW_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_REGISTER));
    }

    @Test
    public void testGetRegistrationFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(personValidator).validate(person, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(personService).savePerson(person);

        mockMvc.perform(post(USER_PAGE_REGISTER_ACTION_ACTION)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        PERSON_FIRST_NAME, person.getFirstName(),
                        PERSON_LAST_NAME, person.getLastName(),
                        PERSON_EMAIL, person.getEmail(),
                        PERSON_ADDRESS, person.getAddress(),
                        PERSON_USER_PSW, person.getUserPassword(),
                        PERSON_CHECK_PSW, person.getCheckPassword(),
                        PERSON_DOB, "1988-01-01T00:00:00.000Z",
                        PERSON_GENDER, person.getGender().getValue(),
                        PERSON_DRIVING_LICENSE_NUMBER, person.getDrivingLicense().getLicenseNumber(),
                        PERSON_DRIVING_LICENSE_OBTAIN_DATE, "2012-01-01T00:00:00.000Z",
                        PERSON_DRIVING_LICENSE_EXPIRE_DATE, "2022-01-01T00:00:00.000Z")))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_LOGIN));
    }
}