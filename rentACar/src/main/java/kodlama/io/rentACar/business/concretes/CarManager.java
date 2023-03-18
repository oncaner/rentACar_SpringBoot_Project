package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.requests.CreateCarRequest;
import kodlama.io.rentACar.business.requests.UpdateCarRequest;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;
import kodlama.io.rentACar.business.rules.CarBusinessRules;
import kodlama.io.rentACar.business.rules.ModelBusinessRules;
import kodlama.io.rentACar.core.utilities.exceptions.CarNotFoundException;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.CarRepository;
import kodlama.io.rentACar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private CarRepository carRepository;
    private ModelMapperService modelMapperService;
    private CarBusinessRules carBusinessRules;
    private ModelBusinessRules modelBusinessRules;

    @Override
    public List<GetAllCarsResponse> getAll() {

        List<Car> cars = carRepository.findAll();

        List<GetAllCarsResponse> carsResponses = cars.stream().map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class)).collect(Collectors.toList());

        return carsResponses;
    }

    @Override
    public GetByIdCarResponse getById(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(String.format("Car not found with: %d", id)));
        GetByIdCarResponse carResponse = this.modelMapperService.forResponse().map(car, GetByIdCarResponse.class);

        return carResponse;
    }

    @Override
    public void add(CreateCarRequest createCarRequest) {
        this.carBusinessRules.checkIfPlateExists(createCarRequest.getPlate());
        this.modelBusinessRules.checkIfModelIdNotExists(createCarRequest.getModelId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        this.carRepository.save(car);
    }

    @Override
    public void update(UpdateCarRequest updateCarRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarRequest.getId());
        this.modelBusinessRules.checkIfModelIdNotExists(updateCarRequest.getModelId());

        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

        this.carRepository.save(car);
    }

    @Override
    public void delete(int id) {
        this.carRepository.deleteById(id);
    }
}
