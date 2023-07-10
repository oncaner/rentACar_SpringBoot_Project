package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.dto.RentalDto;
import kodlama.io.rentACar.dto.RentalDtoConverter;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;
import kodlama.io.rentACar.exception.*;
import kodlama.io.rentACar.model.*;
import kodlama.io.rentACar.repository.RentalRepository;
import kodlama.io.rentACar.service.CarService;
import kodlama.io.rentACar.service.CustomerService;
import kodlama.io.rentACar.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.doThrow;

class RentalServiceImplTest {

    private RentalService rentalService;
    private RentalRepository rentalRepository;
    private CarService carService;
    private CustomerService customerService;
    private CustomerBusinessRules customerBusinessRules;
    private RentalDtoConverter rentalDtoConverter;

    @BeforeEach
    void setUp() {

        rentalRepository = mock(RentalRepository.class);
        carService = mock(CarService.class);
        customerService = mock(CustomerService.class);
        customerBusinessRules = mock(CustomerBusinessRules.class);
        rentalDtoConverter = mock(RentalDtoConverter.class);
        rentalService = new RentalServiceImpl(rentalRepository, carService, customerService,
                customerBusinessRules, rentalDtoConverter);
    }

    @Test
    public void testGetAll_whenGetAllRentalsCalled_shouldReturnListOfRentalDto() {

        Brand brand1 = new Brand(1L, "brand1", List.of());
        Model model1 = new Model(1L, "model1", brand1, List.of());
        Car car1 = new Car(1L, "plate1", 1, 1, 1, model1, List.of());
        Customer customer1 = new Customer(1L, "firstname1", "lastname1", List.of());

        Brand brand2 = new Brand(2L, "brand2", List.of());
        Model model2 = new Model(2L, "model2", brand1, List.of());
        Car car2 = new Car(2L, "plate2", 2, 2, 2, model2, List.of());
        Customer customer2 = new Customer(2L, "firstname2", "lastname2", List.of());

        Rental rental1 = new Rental(1L, car1, customer1, LocalDate.now(), LocalDate.now());
        Rental rental2 = new Rental(2L, car2, customer2, LocalDate.now(), LocalDate.now());

        RentalDto rentalDto1 = new RentalDto(1L, customer1.getId(), customer1.getFirstName(),
                customer1.getLastName(), car1.getId(), car1.getPlate(), car1.getDailyPrice(),
                car1.getModelYear(), model1.getId(), model1.getName(), brand1.getId()
                , brand1.getName(), rental1.getStartDate(), rental1.getEndDate());

        RentalDto rentalDto2 = new RentalDto(2L, customer2.getId(), customer2.getFirstName(),
                customer2.getLastName(), car2.getId(), car2.getPlate(), car2.getDailyPrice(),
                car2.getModelYear(), model2.getId(), model2.getName(), brand2.getId()
                , brand2.getName(), rental2.getStartDate(), rental2.getEndDate());

        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);

        List<RentalDto> rentalDtos = new ArrayList<>();
        rentalDtos.add(rentalDto1);
        rentalDtos.add(rentalDto2);

        when(rentalRepository.findAll()).thenReturn(rentals);
        when(rentalDtoConverter.convertToDto(rental1)).thenReturn(rentalDto1);
        when(rentalDtoConverter.convertToDto(rental2)).thenReturn(rentalDto2);

        List<RentalDto> result = rentalService.getAll();

        verify(rentalRepository).findAll();
        verify(rentalDtoConverter).convertToDto(rental1);
        verify(rentalDtoConverter).convertToDto(rental2);

        assertEquals(result, rentalDtos);
    }

    @Test
    public void testCreate_whenCreateRentalCalledWithRequest_shouldReturnRentalDto() {

        CreateRentalRequest request = new CreateRentalRequest(1L, 1L, LocalDate.now(),
                LocalDate.now());

        Brand brand = new Brand(1L, "brand", List.of());
        Model model = new Model(1L, "model", brand, List.of());
        Car car = new Car(request.getCarId(), "plate", 1, 1, 1, model, List.of());

        Customer customer = new Customer(request.getCustomerId(), "firstname",
                "lastname", List.of());

        Rental rental = new Rental(null, car, customer, request.getStartDate(), request.getEndDate());
        RentalDto rentalDto = new RentalDto(
                rental.getId(), customer.getId(), customer.getFirstName(), customer.getLastName(),
                car.getId(), car.getPlate(), car.getDailyPrice(), car.getModelYear(),
                model.getId(), model.getName(), brand.getId(), brand.getName(),
                rental.getStartDate(), rental.getEndDate());

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

        CreateRentalRequest request = new CreateRentalRequest(1L, 1L, LocalDate.now(),
                LocalDate.now());

        doThrow(CustomerNotFoundException.class).when(customerBusinessRules).checkIfCustomerIdNotExists(
                request.getCustomerId()
        );

        assertThrows(CustomerNotFoundException.class, () -> rentalService.create(request));

        verify(customerBusinessRules).checkIfCustomerIdNotExists(request.getCustomerId());
        verifyNoInteractions(rentalRepository, carService, rentalDtoConverter);
    }

    @Test
    public void testCreate_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        CreateRentalRequest request = new CreateRentalRequest(1L, 1L, LocalDate.now(),
                LocalDate.now());

        doThrow(CarNotFoundException.class).when(carService).getByIdForOtherService(request.getCarId());

        assertThrows(CarNotFoundException.class, () -> rentalService.create(request));

        verify(carService).getByIdForOtherService(request.getCarId());
        verifyNoInteractions(rentalRepository, rentalDtoConverter);
    }

    @Test
    public void testCreate_whenStartDateIsAfterEndDate_shouldThrowInvalidRentDateException() {

        CreateRentalRequest request = new CreateRentalRequest(1L, 1L,
                LocalDate.now().plusDays(7), LocalDate.now());

        assertThrows(InvalidRentDateException.class, () -> rentalService.create(request));

        verifyNoInteractions(rentalDtoConverter, rentalRepository);
    }

    @Test
    public void testCreate_whenCarStateIsNotRentable_shouldThrowCarCannotBeRentedException() {

        CreateRentalRequest request = new CreateRentalRequest(1L, 1L, LocalDate.now(),
                LocalDate.now());

        Car car = new Car();
        car.setState(2);

        when(carService.getByIdForOtherService(request.getCarId())).thenReturn(car);

        assertThrows(CarCannotBeRentedException.class, () -> rentalService.create(request));

        verify(carService).getByIdForOtherService(request.getCarId());
        verifyNoInteractions(rentalDtoConverter, rentalRepository);
    }

    @Test
    public void testDelete_whenRentalIdExists_shouldDeleteRental() {

        Long id = 1L;
        Rental rental = new Rental();
        rental.setId(id);

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












