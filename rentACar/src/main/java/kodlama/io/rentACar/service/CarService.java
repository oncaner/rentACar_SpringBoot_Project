package kodlama.io.rentACar.service;


import kodlama.io.rentACar.dto.response.CarDto;
import kodlama.io.rentACar.dto.request.*;
import kodlama.io.rentACar.model.Car;

import java.util.List;

public interface CarService {
    List<CarDto> getAll();

    List<CarDto> getAllByOrderByDailyPriceAsc();

    List<CarDto> getAllByOrderByDailyPriceDesc();

    List<CarDto> getAllByModelId(Long id);

    List<CarDto> getAllByBrandId(Long id);

    CarDto getById(Long id);

    Car getByIdForOtherService(Long id);

    CarDto create(CreateCarRequest createCarRequest);

    CarDto update(UpdateCarRequest updateCarRequest);

    CarDto updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest);

    CarDto updateByDailyPrice(UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest);

    CarDto updateByModelYear(UpdateCarByModelYearRequest updateCarWithModelYear);

    CarDto updateByState(UpdateCarByStateRequest updateCarByStateRequest);

    void delete(Long id);
}
