package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.dto.converter.RentalDtoConverter;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;
import kodlama.io.rentACar.dto.response.RentalDto;
import kodlama.io.rentACar.exception.CarCannotBeRentedException;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.exception.CustomerNotFoundException;
import kodlama.io.rentACar.exception.InvalidRentDateException;
import kodlama.io.rentACar.exception.RentalNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Customer;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.model.Rental;
import kodlama.io.rentACar.repository.RentalRepository;
import kodlama.io.rentACar.service.CarService;
import kodlama.io.rentACar.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class RentalServiceImplTest {

    @InjectMocks
    private RentalServiceImpl rentalService;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CarService carService;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerBusinessRules customerBusinessRules;
    @Mock
    private RentalDtoConverter rentalDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenGetAllRentalsCalled_shouldReturnListOfRentalDto() {

        Car car = Car.builder().model(Model.builder().brand(Brand.builder().build()).build()).build();
        Customer customer = Customer.builder().build();
        Rental rental = Rental.builder().car(car).customer(customer).build();

        RentalDto rentalDto = RentalDto.builder().build();

        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);

        List<RentalDto> rentalDtos = new ArrayList<>();
        rentalDtos.add(rentalDto);

        when(rentalRepository.findAll()).thenReturn(rentals);
        when(rentalDtoConverter.convertToDto(rental)).thenReturn(rentalDto);

        List<RentalDto> result = rentalService.getAll();

        verify(rentalRepository).findAll();
        verify(rentalDtoConverter).convertToDto(rental);

        assertEquals(result, rentalDtos);
    }

    @Test
    public void testCreate_whenCreateRentalCalledWithRequest_shouldReturnRentalDto() {

        CreateRentalRequest request = CreateRentalRequest.builder()
                .carId(1L).customerId(1L).startDate(LocalDate.now()).endDate(LocalDate.now()).build();

        Car car = Car.builder()
                .id(request.getCarId()).state(1).model(Model.builder()
                        .brand(Brand.builder().build()).build()).build();

        Customer customer = Customer.builder().id(request.getCustomerId()).build();

        Rental rental = Rental.builder()
                .id(null).customer(customer).car(car).startDate(LocalDate.now())
                .endDate(LocalDate.now()).build();

        RentalDto rentalDto = RentalDto.builder()
                .id(rental.getId()).customerId(customer.getId()).carId(car.getId()).build();

        when(carService.getByIdForOtherService(request.getCarId())).thenReturn(car);
        when(customerService.getByIdForOtherService(request.getCustomerId())).thenReturn(customer);
        when(rentalRepository.save(rental)).thenReturn(rental);
        when(rentalDtoConverter.convertToDto(rental)).thenReturn(rentalDto);

        RentalDto result = rentalService.create(request);

        verify(carService).getByIdForOtherService(request.getCarId());
        verify(customerService).getByIdForOtherService(request.getCustomerId());
        verify(rentalRepository).save(rental);
        verify(rentalDtoConverter).convertToDto(rental);

        assertEquals(result, rentalDto);
    }

    @Test
    public void testCreate_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {

        CreateRentalRequest request = CreateRentalRequest.builder().customerId(1L).build();

        doThrow(CustomerNotFoundException.class).when(customerBusinessRules).checkIfCustomerIdNotExists(request.getCustomerId());

        assertThrows(CustomerNotFoundException.class, () -> rentalService.create(request));

        verify(customerBusinessRules).checkIfCustomerIdNotExists(request.getCustomerId());
        verifyNoInteractions(rentalRepository, carService, rentalDtoConverter);
    }

    @Test
    public void testCreate_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        CreateRentalRequest request = CreateRentalRequest.builder().carId(1L).startDate(LocalDate.now()).endDate(LocalDate.now()).build();

        doThrow(CarNotFoundException.class).when(carService).getByIdForOtherService(request.getCarId());

        assertThrows(CarNotFoundException.class, () -> rentalService.create(request));

        verify(carService).getByIdForOtherService(request.getCarId());
        verifyNoInteractions(rentalRepository, rentalDtoConverter);
    }

    @Test
    public void testCreate_whenStartDateIsAfterEndDate_shouldThrowInvalidRentDateException() {

        CreateRentalRequest request = CreateRentalRequest.builder().startDate(LocalDate.now().plusDays(7)).endDate(LocalDate.now()).build();

        assertThrows(InvalidRentDateException.class, () -> rentalService.create(request));

        verifyNoInteractions(rentalDtoConverter, rentalRepository);
    }

    @Test
    public void testCreate_whenCarStateIsNotRentable_shouldThrowCarCannotBeRentedException() {

        CreateRentalRequest request = CreateRentalRequest.builder().carId(1L).startDate(LocalDate.now()).endDate(LocalDate.now()).build();

        Car car = Car.builder().id(request.getCarId()).state(2).build();

        when(carService.getByIdForOtherService(request.getCarId())).thenReturn(car);

        assertThrows(CarCannotBeRentedException.class, () -> rentalService.create(request));

        verify(carService).getByIdForOtherService(request.getCarId());
        verifyNoInteractions(rentalDtoConverter, rentalRepository);
    }

    @Test
    public void testDelete_whenRentalIdExists_shouldDeleteRental() {

        Long id = 1L;

        when(rentalRepository.existsById(id)).thenReturn(true);

        rentalService.delete(id);

        verify(rentalRepository).deleteById(id);
        verify(rentalRepository).existsById(id);
    }

    @Test
    public void testDelete_whenRentalIdDoesNotExist_shouldThrowRentalNotFoundException() {

        Long id = 1L;

        when(rentalRepository.existsById(id)).thenReturn(false);

        assertThrows(RentalNotFoundException.class, () -> rentalService.delete(id));

        verify(rentalRepository).existsById(id);
    }
}
