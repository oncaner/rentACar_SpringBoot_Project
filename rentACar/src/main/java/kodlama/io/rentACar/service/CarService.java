package kodlama.io.rentACar.service;


import kodlama.io.rentACar.dto.requests.CreateCarRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarRequest;
import kodlama.io.rentACar.dto.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdCarResponse;
import kodlama.io.rentACar.model.Car;

import java.util.List;

public interface CarService {
    List<GetAllCarsResponse> getAll();

    List<GetAllCarsResponse> getAllByOrderByDailyPriceAsc();

    List<GetAllCarsResponse> getAllByOrderByDailyPriceDesc();

    List<GetAllCarsByModelIdResponse> getAllByModelId(Long id);

    List<GetAllCarsByBrandIdResponse> getAllByBrandId(Long id);

    GetByIdCarResponse getById(Long id);

    Car create(CreateCarRequest createCarRequest);

    Car update(UpdateCarRequest updateCarRequest);

    Car updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest);

    Car updateByDailyPrice(UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest);

    Car updateByModelYear(UpdateCarByModelYearRequest updateCarWithModelYear);

    Car updateByState(UpdateCarByStateRequest updateCarByStateRequest);

    void delete(Long id);
}
