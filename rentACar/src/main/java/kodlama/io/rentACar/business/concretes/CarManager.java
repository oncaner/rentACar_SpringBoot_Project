package kodlama.io.rentACar.business.concretes;

import kodlama.io.rentACar.business.abstracts.CarService;
import kodlama.io.rentACar.business.requests.*;
import kodlama.io.rentACar.business.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.business.responses.GetAllCarsResponse;
import kodlama.io.rentACar.business.responses.GetByIdCarResponse;
import kodlama.io.rentACar.business.rules.BrandBusinessRules;
import kodlama.io.rentACar.business.rules.CarBusinessRules;
import kodlama.io.rentACar.business.rules.ModelBusinessRules;
import kodlama.io.rentACar.core.utilities.exceptions.CarNotFoundException;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.CarRepository;
import kodlama.io.rentACar.dataAccess.abstracts.ModelRepository;
import kodlama.io.rentACar.entities.concretes.Car;
import kodlama.io.rentACar.entities.concretes.Model;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private CarRepository carRepository;
    private ModelRepository modelRepository;
    private ModelMapperService modelMapperService;
    private CarBusinessRules carBusinessRules;
    private ModelBusinessRules modelBusinessRules;
    private BrandBusinessRules brandBusinessRules;

    @Override
    public List<GetAllCarsResponse> getAll() {

        List<Car> cars = carRepository.findAll();

        List<GetAllCarsResponse> carsResponses = cars.stream().map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class)).collect(Collectors.toList());

        return carsResponses;
    }

    @Override
    public List<GetAllCarsByModelIdResponse> getAllByModelId(int id) {
        this.modelBusinessRules.checkIfModelIdNotExists(id);

        List<Car> cars = carRepository.findAllByModelId(id);

        List<GetAllCarsByModelIdResponse> carsResponse = cars.stream()
                .map(car -> this.modelMapperService.forResponse()
                        .map(car, GetAllCarsByModelIdResponse.class)).toList();

        return carsResponse;
    }

    @Override
    public List<GetAllCarsByBrandIdResponse> getAllByBrandId(int id) {
        this.brandBusinessRules.checkIfBrandIdNotExists(id);

        List<Car> cars = carRepository.findAllByBrandId(id);

        List<GetAllCarsByBrandIdResponse> carsResponse = cars.stream()
                .map(car -> this.modelMapperService.forResponse()
                        .map(car, GetAllCarsByBrandIdResponse.class)).toList();

        return carsResponse;
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

        Car oldCar = carRepository.findById(updateCarRequest.getId()).orElseThrow();
        Model model = modelRepository.findById(oldCar.getModel().getId()).orElseThrow();

        Car updateCar = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        updateCar.setModel(model);

        this.carRepository.save(updateCar);
    }

    @Override
    public void updateCarWithPlate(UpdateCarWithPlateRequest updateCarWithPlateRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarWithPlateRequest.getId());
        this.carBusinessRules.checkIfPlateExists(updateCarWithPlateRequest.getPlate());

        Car car = carRepository.findById(updateCarWithPlateRequest.getId()).orElseThrow();
        car.setPlate(updateCarWithPlateRequest.getPlate());

        carRepository.save(car);
    }

    @Override
    public void updateCarWithDailyPrice(UpdateCarWithDailyPrice updateCarWithDailyPrice) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarWithDailyPrice.getId());

        Car car = carRepository.findById(updateCarWithDailyPrice.getId()).orElseThrow();
        car.setDailyPrice(updateCarWithDailyPrice.getDailyPrice());

        carRepository.save(car);
    }

    @Override
    public void updateCarWithModelYear(UpdateCarWithModelYear updateCarWithModelYear) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarWithModelYear.getId());

        Car car = carRepository.findById(updateCarWithModelYear.getId()).orElseThrow();
        car.setModelYear(updateCarWithModelYear.getModelYear());

        carRepository.save(car);
    }

    @Override
    public void updateCarWithState(UpdateCarWithState updateCarWithState) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarWithState.getId());

        Car car = carRepository.findById(updateCarWithState.getId()).orElseThrow();
        car.setState(updateCarWithState.getState());

        carRepository.save(car);
    }

    @Override
    public void delete(int id) {
        this.carRepository.deleteById(id);
    }
}
