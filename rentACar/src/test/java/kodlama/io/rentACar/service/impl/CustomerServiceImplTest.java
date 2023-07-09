package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.CustomerDto;
import kodlama.io.rentACar.dto.CustomerDtoConverter;
import kodlama.io.rentACar.dto.request.CreateCustomerRequest;
import kodlama.io.rentACar.exception.CustomerNotFoundException;
import kodlama.io.rentACar.model.Customer;
import kodlama.io.rentACar.repository.CustomerRepository;
import kodlama.io.rentACar.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.doThrow;

class CustomerServiceImplTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;
    private ModelMapper modelMapper;
    private CustomerBusinessRules customerBusinessRules;
    private CustomerDtoConverter customerDtoConverter;

    @BeforeEach
    void setUp() {

        customerRepository = mock(CustomerRepository.class);
        modelMapperService = mock(ModelMapperService.class);
        modelMapper = mock(ModelMapper.class);
        customerBusinessRules = mock(CustomerBusinessRules.class);
        customerDtoConverter = mock(CustomerDtoConverter.class);
        customerService = new CustomerServiceImpl(customerRepository, modelMapperService,
                customerBusinessRules, customerDtoConverter);
    }

    @Test
    public void testGetAll_whenGetAllCustomersCalled_shouldReturnListOfCustomerDto() {

        Customer customer1 = new Customer(1L, "firstname1", "lastname1", List.of());
        Customer customer2 = new Customer(2L, "firstname2", "lastname2", List.of());
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        CustomerDto customerDto1 = new CustomerDto(1L, "firstname1", "lastname1");
        CustomerDto customerDto2 = new CustomerDto(2L, "firstname2", "lastname2");
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerDtos.add(customerDto1);
        customerDtos.add(customerDto2);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerDtoConverter.convertToDto(customer1)).thenReturn(customerDto1);
        when(customerDtoConverter.convertToDto(customer2)).thenReturn(customerDto2);

        List<CustomerDto> result = customerService.getAll();

        verify(customerRepository).findAll();
        verify(customerDtoConverter).convertToDto(customer1);
        verify(customerDtoConverter).convertToDto(customer2);

        assertEquals(result, customerDtos);
    }

    @Test
    public void testGetById_whenCustomerIdExists_shouldReturnCustomerDto() {

        Long id = 1L;

        Customer customer = new Customer(1L, "firstname", "lastname", List.of());
        CustomerDto customerDto = new CustomerDto(1L, "firstname", "lastname");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerDtoConverter.convertToDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getById(id);

        verify(customerRepository).findById(id);
        verify(customerDtoConverter).convertToDto(customer);

        assertEquals(result, customerDto);
    }

    @Test
    public void testGetById_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {

        Long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(id));

        verifyNoInteractions(customerDtoConverter);
    }

    @Test
    public void testCreate_whenCreateCustomerCalledWithRequest_shouldReturnCustomerDto() {

        CreateCustomerRequest request = new CreateCustomerRequest("firstname", "lastname");

        Customer customer = new Customer(1L, request.getFirstName(), request.getLastName(), List.of());
        CustomerDto customerDto = new CustomerDto(1L, customer.getFirstName(), customer.getLastName());

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerDtoConverter.convertToDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.create(request);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Customer.class);
        verify(customerRepository).save(customer);
        verify(customerDtoConverter).convertToDto(customer);

        assertEquals(result, customerDto);
    }

    @Test
    public void testDelete_whenCustomerIdExists_shouldDeleteCustomer() {

        Long id = 1L;

        customerService.delete(id);

        verify(customerRepository).deleteById(id);
        verify(customerBusinessRules).checkIfCustomerIdNotExists(id);
    }

    @Test
    public void testDelete_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {

        Long id = 1L;

        doThrow(CustomerNotFoundException.class).when(customerBusinessRules).checkIfCustomerIdNotExists(id);

        assertThrows(CustomerNotFoundException.class, () -> customerService.delete(id));

        verify(customerBusinessRules).checkIfCustomerIdNotExists(id);
        verifyNoInteractions(customerRepository);
    }

}







