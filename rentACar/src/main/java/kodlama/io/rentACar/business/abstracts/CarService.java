package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.business.requests.*;
import kodlama.io.rentACar.business.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;
import kodlama.io.rentACar.entities.concretes.Car;

import java.util.List;

public interface CarService {
    List<GetAllCarsResponse> getAll();

    List<GetAllCarsByModelIdResponse> getAllByModelId(int id);

    List<GetAllCarsByBrandIdResponse> getAllByBrandId(int id);

    GetByIdCarResponse getById(int id);

    Car create(CreateCarRequest createCarRequest);

    Car update(UpdateCarRequest updateCarRequest);

    Car updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest);

    Car updateByDailyPrice(UpdateCarByDailyPrice updateCarByDailyPrice);

    Car updateByModelYear(UpdateCarByModelYearRequest updateCarWithModelYear);

    Car updateByState(UpdateCarByStateRequest updateCarByStateRequest);

    void delete(int id);
}
