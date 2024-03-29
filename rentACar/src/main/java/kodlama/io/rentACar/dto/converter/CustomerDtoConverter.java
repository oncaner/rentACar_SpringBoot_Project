package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.dto.response.CustomerDto;
import kodlama.io.rentACar.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {

    public CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }

}
