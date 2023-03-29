package kodlama.io.rentACar.webApi.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.requests.*;
import kodlama.io.rentACar.business.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;
import kodlama.io.rentACar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {

    private CarService carService;

    @GetMapping()
    public ResponseEntity<List<GetAllCarsResponse>> getAll() {
        List<GetAllCarsResponse> responseList = this.carService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdCarResponse> getById(@PathVariable int id) {
        GetByIdCarResponse response = this.carService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-model-id/{id}")
    public ResponseEntity<List<GetAllCarsByModelIdResponse>> getAllByModelId(@PathVariable int id) {
        List<GetAllCarsByModelIdResponse> responseList = this.carService.getAllByModelId(id);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-brand-id/{id}")
    public ResponseEntity<List<GetAllCarsByBrandIdResponse>> getAllByBrandId(@PathVariable int id) {
        List<GetAllCarsByBrandIdResponse> responseList = this.carService.getAllByBrandId(id);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Car> create(@RequestBody @Valid CreateCarRequest createCarRequest) {
        Car car = this.carService.create(createCarRequest);

        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Car> update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
        Car car = this.carService.update(updateCarRequest);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PatchMapping("/update/by-plate")
    public ResponseEntity<Car> updateByPlate(@RequestBody @Valid UpdateCarByPlateRequest updateCarByPlateRequest) {
        Car car = this.carService.updateByPlate(updateCarByPlateRequest);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PatchMapping("update/by-daily-price")
    public ResponseEntity<Car> updateByDailyPrice(@RequestBody @Valid UpdateCarByDailyPrice updateCarByDailyPrice) {
        Car car = this.carService.updateByDailyPrice(updateCarByDailyPrice);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PatchMapping("update/by-model-year")
    public ResponseEntity<Car> updateByModelYear(@RequestBody @Valid UpdateCarByModelYearRequest updateCarByModelYearRequest) {
        Car car = this.carService.updateByModelYear(updateCarByModelYearRequest);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PatchMapping("update/by-state")
    public ResponseEntity<Car> updateByState(@RequestBody @Valid UpdateCarByStateRequest updateCarByStateRequest) {
        Car car = this.carService.updateByState(updateCarByStateRequest);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.carService.delete(id);
    }
}
