package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.converter.RentalDto;
import kodlama.io.rentACar.dto.converter.RentalDtoConverter;
import kodlama.io.rentACar.dto.requests.CreateRentalRequest;
import kodlama.io.rentACar.model.Rental;
import kodlama.io.rentACar.repository.RentalRepository;
import kodlama.io.rentACar.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;
    private final RentalDtoConverter rentalDtoConverter;

    @GetMapping()
    public ResponseEntity<List<RentalDto>> getAll() {
        List<RentalDto> rentalDtos = this.rentalService.getAll();

        return new ResponseEntity<>(rentalDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RentalDto> create(@RequestBody @Valid CreateRentalRequest createRentalRequest) {

        Rental rental = this.rentalService.create(createRentalRequest);

        return new ResponseEntity<>(this.rentalDtoConverter.convertToDto(rental), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.rentalService.delete(id);

        return new ResponseEntity<>("Successfully deleted.",HttpStatus.OK);
    }

}
