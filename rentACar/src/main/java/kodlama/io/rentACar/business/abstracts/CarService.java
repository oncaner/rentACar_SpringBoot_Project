package kodlama.io.rentACar.business.abstracts;

import kodlama.io.rentACar.business.requests.*;
import kodlama.io.rentACar.business.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;

import java.util.List;

public interface CarService {
    List<GetAllCarsResponse> getAll();

    List<GetAllCarsByModelIdResponse> getAllByModelId(int id);

    List<GetAllCarsByBrandIdResponse> getAllByBrandId(int id);

    GetByIdCarResponse getById(int id);

    void add(CreateCarRequest createCarRequest);

    void update(UpdateCarRequest updateCarRequest);

    void updateCarWithPlate(UpdateCarWithPlateRequest updateCarWithPlateRequest);

    void updateCarWithDailyPrice(UpdateCarWithDailyPrice updateCarWithDailyPrice);

    void updateCarWithModelYear(UpdateCarWithModelYear updateCarWithModelYear);

    void updateCarWithState(UpdateCarWithState updateCarWithState);

    void delete(int id);
}
