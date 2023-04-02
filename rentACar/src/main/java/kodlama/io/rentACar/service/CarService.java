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

    List<GetAllCarsByModelIdResponse> getAllByModelId(int id);

    List<GetAllCarsByBrandIdResponse> getAllByBrandId(int id);

    GetByIdCarResponse getById(int id);

    Car create(CreateCarRequest createCarRequest);

    Car update(UpdateCarRequest updateCarRequest);

    Car updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest);

    Car updateByDailyPrice(UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest);

    Car updateByModelYear(UpdateCarByModelYearRequest updateCarWithModelYear);

    Car updateByState(UpdateCarByStateRequest updateCarByStateRequest);

    void delete(int id);
}
