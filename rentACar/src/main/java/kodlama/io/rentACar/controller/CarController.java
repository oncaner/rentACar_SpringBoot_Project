package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.converter.CarDto;
import kodlama.io.rentACar.dto.converter.CarDtoConverter;
import kodlama.io.rentACar.dto.requests.CreateCarRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdCarResponse;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final CarDtoConverter carDtoConverter;

    @GetMapping()
    public ResponseEntity<List<GetAllCarsResponse>> getAll() {
        List<GetAllCarsResponse> responseList = this.carService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-price-asc")
    public ResponseEntity<List<GetAllCarsResponse>> getAllByOrderByDailyPriceAsc() {
        List<GetAllCarsResponse> responseList = this.carService.getAllByOrderByDailyPriceAsc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-price-desc")
    public ResponseEntity<List<GetAllCarsResponse>> getAllByOrderByDailyPriceDesc() {
        List<GetAllCarsResponse> responseList = this.carService.getAllByOrderByDailyPriceDesc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdCarResponse> getById(@PathVariable Long id) {
        GetByIdCarResponse response = this.carService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-model-id/{id}")
    public ResponseEntity<List<GetAllCarsByModelIdResponse>> getAllByModelId(@PathVariable Long id) {
        List<GetAllCarsByModelIdResponse> responseList = this.carService.getAllByModelId(id);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-brand-id/{id}")
    public ResponseEntity<List<GetAllCarsByBrandIdResponse>> getAllByBrandId(@PathVariable Long id) {
        List<GetAllCarsByBrandIdResponse> responseList = this.carService.getAllByBrandId(id);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CarDto> create(@RequestBody @Valid CreateCarRequest createCarRequest) {
        Car car = this.carService.create(createCarRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CarDto> update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
        Car car = this.carService.update(updateCarRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.OK);
    }

    @PatchMapping("/update/by-plate")
    public ResponseEntity<CarDto> updateByPlate(@RequestBody @Valid UpdateCarByPlateRequest updateCarByPlateRequest) {
        Car car = this.carService.updateByPlate(updateCarByPlateRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.OK);
    }

    @PatchMapping("update/by-daily-price")
    public ResponseEntity<CarDto> updateByDailyPrice(@RequestBody @Valid UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest) {
        Car car = this.carService.updateByDailyPrice(updateCarByDailyPriceRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.OK);
    }

    @PatchMapping("update/by-model-year")
    public ResponseEntity<CarDto> updateByModelYear(@RequestBody @Valid UpdateCarByModelYearRequest updateCarByModelYearRequest) {
        Car car = this.carService.updateByModelYear(updateCarByModelYearRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.OK);
    }

    @PatchMapping("update/by-state")
    public ResponseEntity<CarDto> updateByState(@RequestBody @Valid UpdateCarByStateRequest updateCarByStateRequest) {
        Car car = this.carService.updateByState(updateCarByStateRequest);

        return new ResponseEntity<>(carDtoConverter.convertToDto(car), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        this.carService.delete(id);
    }
}
