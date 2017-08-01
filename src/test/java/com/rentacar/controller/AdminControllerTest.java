package com.rentacar.controller;

import com.rentacar.config.TestWebConfig;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.testutils.TestDataUtil;
import com.rentacar.validator.AddCarValidator;
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

import static com.rentacar.util.CarModelConstants.*;
import static com.rentacar.util.PageActionsConstants.*;
import static com.rentacar.util.PageNavigationConstants.*;
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
@ContextConfiguration(classes = TestWebConfig.class)
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
    private AddCarValidator addCarValidator;
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
        mockMvc.perform(get(ADMIN_PAGE_HOME_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
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
        mockMvc.perform(post(ADMIN_PAGE_FILTER_CARS_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testGetAddCarForm() throws Exception {
        mockMvc.perform(get(ADMIN_PAGE_ADD_CAR_VIEW_ACTION))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_ADD_CAR));
    }

    @Test
    public void testGetAddCarFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(addCarValidator).validate(car, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(adminRentACarService).addACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);

        mockMvc.perform(post(ADMIN_PAGE_ADD_CAR_ACTION).param("addCar", "addCar")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        CAR_WIN_CODE, car.getWinCode(),
                        CAR_MANUFACTURER, car.getManufacturer(),
                        CAR_MODEL, car.getModel(),
                        CAR_TYPE, car.getType().getValue(),
                        CAR_YEAR, car.getYearOfProduction(),
                        CAR_REGISTRATION_NUMBER, car.getRegistrationNumber(),
                        CAR_ENGINE_VOLUME, "" + car.getEngineVolume(),
                        CAR_FUEL_TYPE, car.getFuelType().getValue(),
                        CAR_TRANSMISSION, car.getTransmission().getValue(),
                        CAR_ECONOMY_CLASS, car.getEconomyClass().getValue(),
                        CAR_OPTIONS, car.getOptions().getValue(),
                        CAR_PRICE, "" + car.getCarPrice())))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testGetEditCarForm() throws Exception {
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("editCar", "editCar"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_EDIT_CAR));
    }

    @Test
    public void testGetEditCarFormAction() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        doNothing().when(editCarValidator).validate(car, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);

        doNothing().when(carService).updateCar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);

        mockMvc.perform(post(ADMIN_PAGE_EDIT_CAR_ACTION).param("edit", "edit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestDataUtil.buildUrlEncodedFormEntity(
                        CAR_WIN_CODE, car.getWinCode(),
                        CAR_MANUFACTURER, car.getManufacturer(),
                        CAR_MODEL, "TESTT",
                        CAR_TYPE, car.getType().getValue(),
                        CAR_YEAR, car.getYearOfProduction(),
                        CAR_REGISTRATION_NUMBER, car.getRegistrationNumber(),
                        CAR_ENGINE_VOLUME, "" + car.getEngineVolume(),
                        CAR_FUEL_TYPE, car.getFuelType().getValue(),
                        CAR_TRANSMISSION, car.getTransmission().getValue(),
                        CAR_ECONOMY_CLASS, car.getEconomyClass().getValue(),
                        CAR_OPTIONS, car.getOptions().getValue(),
                        CAR_PRICE, "" + 120.0)))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testViewCarActionView() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(get(ADMIN_PAGE_VIEW_CAR_VIEW_ACTION).param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_VIEW_CAR));
    }

    @Test
    public void testCancelBookingAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).cancelBookingByCar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("cancelBooking", "cancelBooking").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testCancelRentAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("cancelRent", "cancelRent").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testEditCarAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("editCar", "editCar").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_EDIT_CAR));
    }

    @Test
    public void testUnSuspendAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).unSuspendACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("unSuspend", "unSuspend").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testSuspendAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        doNothing().when(adminRentACarService).suspendACar(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("suspend", "suspend").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

    @Test
    public void testCancelAction() throws Exception {
        when(carService.findCarByWinCode(car.getWinCode())).thenReturn(car);
        List<Car> carList = Arrays.asList(new Car());
        when(adminRentACarService.searchACar(carFilter)).thenReturn(carList);
        mockMvc.perform(post(ADMIN_PAGE_ACTION_FORM_ACTION).param("cancel", "cancel").param("carWinCode", car.getWinCode()))
                .andExpect(status().isOk())
                .andExpect(view().name(ADMIN_PAGE_HOME));
    }

}