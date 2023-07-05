package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.CarDto;
import kodlama.io.rentACar.dto.request.CreateCarRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @GetMapping()
    public ResponseEntity<List<CarDto>> getAll() {

        return new ResponseEntity<>(this.carService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/order-by-price-asc")
    public ResponseEntity<List<CarDto>> getAllByOrderByDailyPriceAsc() {

        return new ResponseEntity<>(this.carService.getAllByOrderByDailyPriceAsc(), HttpStatus.OK);
    }

    @GetMapping("/order-by-price-desc")
    public ResponseEntity<List<CarDto>> getAllByOrderByDailyPriceDesc() {

        return new ResponseEntity<>(this.carService.getAllByOrderByDailyPriceDesc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getById(@PathVariable Long id) {

        return new ResponseEntity<>(this.carService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-model-id/{id}")
    public ResponseEntity<List<CarDto>> getAllByModelId(@PathVariable Long id) {

        return new ResponseEntity<>(this.carService.getAllByModelId(id), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-brand-id/{id}")
    public ResponseEntity<List<CarDto>> getAllByBrandId(@PathVariable Long id) {

        return new ResponseEntity<>(this.carService.getAllByBrandId(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CarDto> create(@RequestBody @Valid CreateCarRequest createCarRequest) {

        return new ResponseEntity<>(this.carService.create(createCarRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CarDto> update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {

        return new ResponseEntity<>(this.carService.update(updateCarRequest), HttpStatus.OK);
    }

    @PatchMapping("/update/by-plate")
    public ResponseEntity<CarDto> updateByPlate(@RequestBody @Valid UpdateCarByPlateRequest updateCarByPlateRequest) {

        return new ResponseEntity<>(this.carService.updateByPlate(updateCarByPlateRequest), HttpStatus.OK);
    }

    @PatchMapping("update/by-daily-price")
    public ResponseEntity<CarDto> updateByDailyPrice(@RequestBody @Valid UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest) {

        return new ResponseEntity<>(this.carService.updateByDailyPrice(updateCarByDailyPriceRequest), HttpStatus.OK);
    }

    @PatchMapping("update/by-model-year")
    public ResponseEntity<CarDto> updateByModelYear(@RequestBody @Valid UpdateCarByModelYearRequest updateCarByModelYearRequest) {

        return new ResponseEntity<>(this.carService.updateByModelYear(updateCarByModelYearRequest), HttpStatus.OK);
    }

    @PatchMapping("update/by-state")
    public ResponseEntity<CarDto> updateByState(@RequestBody @Valid UpdateCarByStateRequest updateCarByStateRequest) {

        return new ResponseEntity<>(this.carService.updateByState(updateCarByStateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.carService.delete(id);

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
