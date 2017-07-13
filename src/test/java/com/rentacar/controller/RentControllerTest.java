package com.rentacar.controller;

import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.UserRentACarService;
import com.rentacar.testutils.TestDataUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.rentacar.util.PageActionsConstants.*;
import static com.rentacar.util.PageNavigationConstants.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
public class RentControllerTest {
    private Car car;
    private Person person;
    @InjectMocks
    private RentController controller;
    @Mock
    private UserRentACarService userRentACarService;
    @Mock
    private CarService carService;
    @Mock
    private View mockView;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(mockView)
                .build();
        car = TestDataUtil.getMockCar();
        person = TestDataUtil.getMockPerson();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAvailableCarsPage() throws Exception {
        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);
        mockMvc.perform(get(USER_PAGE_AVAILABLE_CARS_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_AVAILABLE_CARS));
    }

    @Test
    public void testBookCarAction() throws Exception {
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", TestDataUtil.getMockPerson());
        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);
        when(userRentACarService.bookACar(car, person)).thenReturn(any());
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post(USER_PAGE_BOOK_CAR_ACTION_ACTION).sessionAttrs(sessionAttr).param("book", "book").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_AVAILABLE_CARS));
    }

    @Test
    public void testRentCarAction() throws Exception {
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", TestDataUtil.getMockPerson());
        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);
        doNothing().when(userRentACarService).rentACar(car, person);
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post(USER_PAGE_RENT_CAR_ACTION_ACTION).sessionAttrs(sessionAttr).param("rent", "rent").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_AVAILABLE_CARS));
    }

    @Test
    public void testViewCarActionView() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get(USER_PAGE_CAR_LIST_ACTION_ACTION).param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_VIEW_CAR));
    }

    @Test
    public void testViewCarActionRent() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get(USER_PAGE_CAR_LIST_ACTION_ACTION).param("rent", "rent").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_RENT_CAR));
    }

    @Test
    public void testViewCarActionBook() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get(USER_PAGE_CAR_LIST_ACTION_ACTION).param("book", "book").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_BOOK_CAR));
    }

    @Test
    public void testCancelAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post(USER_PAGE_RENT_CAR_ACTION_ACTION).param("cancel", "cancel").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE_AVAILABLE_CARS));
    }
}