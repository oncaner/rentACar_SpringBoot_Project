package kodlama.io.rentACar.businessrules;

import kodlama.io.rentACar.exception.CustomerNotFoundException;
import kodlama.io.rentACar.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerBusinessRules {

    private CustomerRepository customerRepository;

    public void checkIfCustomerIdNotExists(Long id) {
        if (!this.customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(String.format("Customer not found with: %d", id));
        }
    }

}
