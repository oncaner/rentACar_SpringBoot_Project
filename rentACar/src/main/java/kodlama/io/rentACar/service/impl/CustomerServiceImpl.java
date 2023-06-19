package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.config.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.CustomerDto;
import kodlama.io.rentACar.dto.requests.CreateCustomerRequest;
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

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> this.modelMapperService.forResponse()
                        .map(customer, CustomerDto.class)).toList();

        return customerDtos;
    }

    @Override
    public CustomerDto getById(int id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer not found with %d", id)));
        CustomerDto customerDto = this.modelMapperService.forResponse().map(customer, CustomerDto.class);

        return customerDto;
    }

    @Override
    public Customer create(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);

        return this.customerRepository.save(customer);
    }

    @Override
    public void delete(int id) {
        this.customerBusinessRules.checkIfCustomerIdNotExists(id);

        this.customerRepository.deleteById(id);
    }
}
