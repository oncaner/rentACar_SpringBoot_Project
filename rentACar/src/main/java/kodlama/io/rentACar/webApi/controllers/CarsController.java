package kodlama.io.rentACar.webApi.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.requests.*;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {

    private CarService carService;

    @GetMapping()
    public List<GetAllCarsResponse> getAll() {
        return carService.getAll();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@RequestBody @Valid CreateCarRequest createCarRequest) {
        this.carService.add(createCarRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.carService.delete(id);
    }

    @GetMapping("/{id}")
    public GetByIdCarResponse getById(@PathVariable int id) {
        return carService.getById(id);
    }

    @PutMapping()
    public void update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
        this.carService.update(updateCarRequest);
    }

    @PatchMapping("/update-with-plate")
    public void updateWithPlate(@RequestBody @Valid UpdateCarWithPlateRequest updateCarWithPlateRequest){
        this.carService.updateCarWithPlate(updateCarWithPlateRequest);
    }

    @PatchMapping("update-with-daily-price")
    public void updateWithDailyPrice(@RequestBody @Valid UpdateCarWithDailyPrice updateCarWithDailyPrice){
        this.carService.updateCarWithDailyPrice(updateCarWithDailyPrice);
    }

    @PatchMapping("update-with-model-year")
    public void updateWithModelYear(@RequestBody @Valid UpdateCarWithModelYear updateCarWithModelYear){
        this.carService.updateCarWithModelYear(updateCarWithModelYear);
    }

    @PatchMapping("update-with-state")
    public void updateWithState(@RequestBody @Valid UpdateCarWithState updateCarWithState){
        this.carService.updateCarWithState(updateCarWithState);
    }
}
