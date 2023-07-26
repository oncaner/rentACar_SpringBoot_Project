package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.response.CustomerDto;
import kodlama.io.rentACar.dto.request.CreateCustomerRequest;
import kodlama.io.rentACar.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getAll() {
        List<CustomerDto> customerDtos = this.customerService.getAll();

        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        CustomerDto customerDto = this.customerService.getById(id);

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {

        return new ResponseEntity<>(this.customerService.create(createCustomerRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.customerService.delete(id);

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
