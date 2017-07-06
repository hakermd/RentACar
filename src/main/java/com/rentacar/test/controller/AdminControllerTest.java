package com.rentacar.test.controller;

import com.rentacar.controller.AdminController;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.test.testutils.TestDataUtil;
import com.rentacar.validator.CarValidator;
import com.rentacar.validator.EditCarValidator;
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

import static org.junit.Assert.assertEquals;
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
public class AdminControllerTest {
    private Car car;
    @InjectMocks
    private AdminController controller;
    @Mock
    private AdminRentACarService adminRentACarService;
    @Mock
    private CarService carService;
    @Mock
    private CarFilter carFilter;
    @Mock
    private EditCarValidator editCarValidator;
    @Mock
    private CarValidator carValidator;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAdminHomePage() throws Exception {
        mockMvc.perform(get("/adminHome"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testFilterAdminCars() throws Exception {
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        for (Car c : carList) {
            assertEquals(carFilter.getEconomyClass(), c.getEconomyClass());
            assertEquals(carFilter.getCarType(), c.getType());
            assertEquals(carFilter.getOptions(), c.getOptions());
            assertEquals(carFilter.getTransmission(), c.getTransmission());
            assertEquals(carFilter.getYearOfProduction(), c.getYearOfProduction());
            assertEquals(carFilter.getCarAvailability(), c.getAvailability());
        }
        mockMvc.perform(post("/filterAdminCars"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testGetAddCarForm() throws Exception {
        mockMvc.perform(get("/addCarAdmin"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCarAdmin"));
    }

    @Test
    public void testGetAddCarFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(carValidator).validate(car, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(adminRentACarService).addACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);

        mockMvc.perform(post("/addCar.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        "winCode", car.getWinCode(),
                        "manufacturer", car.getManufacturer(),
                        "model", car.getModel(),
                        "type", car.getType().getValue(),
                        "yearOfProduction", car.getYearOfProduction(),
                        "registrationNumber", car.getRegistrationNumber(),
                        "engineVolume", "" + car.getEngineVolume(),
                        "fuelType", car.getFuelType().getValue(),
                        "transmission", car.getTransmission().getValue(),
                        "economyClass", car.getEconomyClass().getValue(),
                        "options", car.getOptions().getValue(),
                        "carPrice", "" + car.getCarPrice())))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testGetEditCarForm() throws Exception {
        mockMvc.perform(get("/editCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCarAdmin"));
    }

    @Test
    public void testGetEditCarFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(editCarValidator).validate(car, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(carService).updateCar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);

        mockMvc.perform(post("/editCar.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        "winCode", car.getWinCode(),
                        "manufacturer", car.getManufacturer(),
                        "model", "TESTT",
                        "type", car.getType().getValue(),
                        "yearOfProduction", car.getYearOfProduction(),
                        "registrationNumber", car.getRegistrationNumber(),
                        "engineVolume", "" + car.getEngineVolume(),
                        "fuelType", car.getFuelType().getValue(),
                        "transmission", car.getTransmission().getValue(),
                        "economyClass", car.getEconomyClass().getValue(),
                        "options", car.getOptions().getValue(),
                        "carPrice", "120")))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testViewCarActionView() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post("/carListAdminAction").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("viewCarAdmin"));
    }

    @Test
    public void testViewCarActionCancelBook() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).cancelBookingByCar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post("/carListAdminAction").param("action", "CANCEL BOOK").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testViewCarActionCancelRent() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post("/carListAdminAction").param("action", "CANCEL RENT").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testViewCarActionEditCar() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post("/carListAdminAction").param("action", "EDIT CAR").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("editCarAdmin"));
    }

    @Test
    public void testViewCarActionUnSuspend() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).unsuspendACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post("/carListAdminAction").param("action", "UNSUSPEND").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testViewCarActionSuspend() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).suspendACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post("/carListAdminAction").param("action", "SUSPEND").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    public void testViewCarActionCancel() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post("/carListAdminAction").param("action", "CANCEL").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

}