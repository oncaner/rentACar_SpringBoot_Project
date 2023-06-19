package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.converter.CustomerDto;
import kodlama.io.rentACar.dto.requests.CreateCustomerRequest;
import kodlama.io.rentACar.model.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAll();

    CustomerDto getById(int id);

    Customer create(CreateCustomerRequest createCustomerRequest);

    void delete(int id);

}
