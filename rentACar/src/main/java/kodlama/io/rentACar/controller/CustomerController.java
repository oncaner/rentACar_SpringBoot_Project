package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.converter.CustomerDto;
import kodlama.io.rentACar.dto.converter.CustomerDtoConverter;
import kodlama.io.rentACar.dto.requests.CreateCustomerRequest;
import kodlama.io.rentACar.model.Customer;
import kodlama.io.rentACar.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDtoConverter customerDtoConverter;

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getAll() {
        List<CustomerDto> customerDtos = this.customerService.getAll();

        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable int id) {
        CustomerDto customerDto = this.customerService.getById(id);

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.customerService.create(createCustomerRequest);

        return new ResponseEntity<>(this.customerDtoConverter.convertToDto(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.customerService.delete(id);
    }
}
