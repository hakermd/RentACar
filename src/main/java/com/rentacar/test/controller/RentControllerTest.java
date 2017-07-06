package com.rentacar.test.controller;

import com.rentacar.controller.RentController;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.services.CarService;
import com.rentacar.services.UserRentACarService;
import com.rentacar.test.testutils.TestDataUtil;
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
        mockMvc.perform(get("/availableCars"))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"));
    }

    @Test
    public void testBookCarAction() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("user", TestDataUtil.getMockPerson());
        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);
        when(userRentACarService.bookACar(car, person)).thenReturn(any());
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post("/bookCar.do").sessionAttrs(sessionattr).param("action", "BOOK").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"));
    }

    @Test
    public void testRentCarAction() throws Exception {
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("user", TestDataUtil.getMockPerson());
        List<Car> carList = Arrays.asList(new Car());
        when(carService.showAllAvailableCars()).thenReturn(carList);
        doNothing().when(userRentACarService).rentACar(car, person);
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post("/rentCar.do").sessionAttrs(sessionattr).param("action", "RENT").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"));
    }

    @Test
    public void testViewCarActionView() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get("/carListAction").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("viewCar"));
    }

    @Test
    public void testViewCarActionRent() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get("/carListAction").param("action", "RENT").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("rentCar"));
    }

    @Test
    public void testViewCarActionBook() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get("/carListAction").param("action", "BOOK").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("bookCar"));
    }

    @Test
    public void testViewCarActionCancel() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get("/carListAction").param("action", "CANCEL").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"));
    }
}