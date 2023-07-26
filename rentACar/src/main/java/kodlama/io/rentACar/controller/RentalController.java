package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.response.RentalDto;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;
import kodlama.io.rentACar.service.RentalService;
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
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping()
    public ResponseEntity<List<RentalDto>> getAll() {
        List<RentalDto> rentalDtos = this.rentalService.getAll();

        return new ResponseEntity<>(rentalDtos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RentalDto> create(@RequestBody @Valid CreateRentalRequest createRentalRequest) {

        return new ResponseEntity<>(this.rentalService.create(createRentalRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.rentalService.delete(id);

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
