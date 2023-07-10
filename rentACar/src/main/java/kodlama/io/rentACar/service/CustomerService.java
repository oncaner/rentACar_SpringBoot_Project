package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.CustomerDto;
import kodlama.io.rentACar.dto.request.CreateCustomerRequest;
import kodlama.io.rentACar.model.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAll();

    CustomerDto getById(Long id);

    Customer getByIdForOtherService(Long id);

    CustomerDto create(CreateCustomerRequest createCustomerRequest);

    void delete(Long id);

}
