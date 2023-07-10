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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;
    private final CustomerBusinessRules customerBusinessRules;
    private final CustomerDtoConverter customerDtoConverter;

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customers = this.customerRepository.findAll();

        return customers.stream()
                .map(this.customerDtoConverter::convertToDto).toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found with %d", id)));

        return this.customerDtoConverter.convertToDto(customer);
    }

    @Override
    public Customer getByIdForOtherService(Long id) {

        return this.customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with: " + id)
        );
    }

    @Override
    public CustomerDto create(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);

        return this.customerDtoConverter.convertToDto(this.customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        this.customerBusinessRules.checkIfCustomerIdNotExists(id);

        this.customerRepository.deleteById(id);
    }
}
