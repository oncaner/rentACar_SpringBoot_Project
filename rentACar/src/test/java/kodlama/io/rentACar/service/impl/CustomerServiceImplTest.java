package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.CustomerDtoConverter;
import kodlama.io.rentACar.dto.request.CreateCustomerRequest;
import kodlama.io.rentACar.dto.response.CustomerDto;
import kodlama.io.rentACar.exception.CustomerNotFoundException;
import kodlama.io.rentACar.model.Customer;
import kodlama.io.rentACar.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CustomerBusinessRules customerBusinessRules;
    @Mock
    private CustomerDtoConverter customerDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenGetAllCustomersCalled_shouldReturnListOfCustomerDto() {

        Customer customer1 = Customer.builder().id(1L).firstName("firstname").lastName("lastname").build();
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        CustomerDto customerDto1 = CustomerDto.builder()
                .id(customer1.getId()).firstName(customer1.getFirstName())
                .lastName(customer1.getLastName()).build();
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerDtos.add(customerDto1);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerDtoConverter.convertToDto(customer1)).thenReturn(customerDto1);

        List<CustomerDto> result = customerService.getAll();

        verify(customerRepository).findAll();
        verify(customerDtoConverter).convertToDto(customer1);

        assertEquals(result, customerDtos);
    }

    @Test
    public void testGetById_whenCustomerIdExists_shouldReturnCustomerDto() {

        Long id = 1L;

        Customer customer = Customer.builder().id(1L).firstName("firstname").lastName("lastname").build();

        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).build();

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
    public void testGetByIdForOtherService_whenCustomerIdExists_shouldReturnCustomer() {

        Long id = 1L;

        Customer customer = Customer.builder().id(1L).firstName("firstname").lastName("lastname").build();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Customer result = customerService.getByIdForOtherService(id);

        assertEquals(result, customer);

        verify(customerRepository).findById(id);
    }

    @Test
    public void testGetByIdForOtherService_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {

        Long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->
                customerService.getByIdForOtherService(id));

        verify(customerRepository).findById(id);
    }

    @Test
    public void testCreate_whenCreateCustomerCalledWithRequest_shouldReturnCustomerDto() {

        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .firstName("firstname").lastName("lastname").build();

        Customer customer = Customer.builder()
                .id(1L).firstName(request.getFirstName()).lastName(request.getLastName()).build();

        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).build();

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