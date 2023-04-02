package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.CarBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.config.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.requests.CreateCarRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarRequest;
import kodlama.io.rentACar.dto.requests.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.dto.responses.GetAllCarsByBrandIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsByModelIdResponse;
import kodlama.io.rentACar.dto.responses.GetAllCarsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdCarResponse;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.repository.ModelRepository;
import kodlama.io.rentACar.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private ModelRepository modelRepository;
    private ModelMapperService modelMapperService;
    private CarBusinessRules carBusinessRules;
    private ModelBusinessRules modelBusinessRules;
    private BrandBusinessRules brandBusinessRules;

    @Override
    public List<GetAllCarsResponse> getAll() {
        List<Car> cars = this.carRepository.findAll();

        List<GetAllCarsResponse> carsResponses = cars.stream()
                .map(car -> this.modelMapperService.forResponse()
                        .map(car, GetAllCarsResponse.class)).collect(Collectors.toList());

        return carsResponses;
    }

    @Override
    public List<GetAllCarsByModelIdResponse> getAllByModelId(int id) {
        this.modelBusinessRules.checkIfModelIdNotExists(id);

        List<Car> cars = this.carRepository.findAllByModelId(id);

        List<GetAllCarsByModelIdResponse> carsResponse = cars.stream()
                .map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsByModelIdResponse.class)).toList();

        return carsResponse;
    }

    @Override
    public List<GetAllCarsByBrandIdResponse> getAllByBrandId(int id) {
        this.brandBusinessRules.checkIfBrandIdNotExists(id);

        List<Car> cars = this.carRepository.findAllByBrandId(id);

        List<GetAllCarsByBrandIdResponse> carsResponse = cars.stream()
                .map(car -> this.modelMapperService.forResponse()
                        .map(car, GetAllCarsByBrandIdResponse.class)).toList();

        return carsResponse;
    }

    @Override
    public GetByIdCarResponse getById(int id) {
        Car car = this.carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(String.format("Car not found with: %d", id)));
        GetByIdCarResponse carResponse = this.modelMapperService.forResponse().map(car, GetByIdCarResponse.class);

        return carResponse;
    }

    @Override
    public Car create(CreateCarRequest createCarRequest) {
        this.carBusinessRules.checkIfPlateExists(createCarRequest.getPlate());
        this.modelBusinessRules.checkIfModelIdNotExists(createCarRequest.getModelId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        return this.carRepository.save(car);
    }

    @Override
    public Car update(UpdateCarRequest updateCarRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarRequest.getId());

        Car oldCar = this.carRepository.findById(updateCarRequest.getId()).orElseThrow();
        Model model = this.modelRepository.findById(oldCar.getModel().getId()).orElseThrow();

        Car updateCar = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        updateCar.setModel(model);

        return this.carRepository.save(updateCar);
    }

    @Override
    public Car updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByPlateRequest.getId());
        this.carBusinessRules.checkIfPlateExists(updateCarByPlateRequest.getPlate());

        Car car = this.carRepository.findById(updateCarByPlateRequest.getId()).orElseThrow();
        car.setPlate(updateCarByPlateRequest.getPlate());

        return this.carRepository.save(car);
    }

    @Override
    public Car updateByDailyPrice(UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByDailyPriceRequest.getId());

        Car car = carRepository.findById(updateCarByDailyPriceRequest.getId()).orElseThrow();
        car.setDailyPrice(updateCarByDailyPriceRequest.getDailyPrice());

        return this.carRepository.save(car);
    }

    @Override
    public Car updateByModelYear(UpdateCarByModelYearRequest updateCarByModelYearRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByModelYearRequest.getId());

        Car car = this.carRepository.findById(updateCarByModelYearRequest.getId()).orElseThrow();
        car.setModelYear(updateCarByModelYearRequest.getModelYear());

        return this.carRepository.save(car);
    }

    @Override
    public Car updateByState(UpdateCarByStateRequest updateCarByStateRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByStateRequest.getId());

        Car car = this.carRepository.findById(updateCarByStateRequest.getId()).orElseThrow();
        car.setState(updateCarByStateRequest.getState());

        return this.carRepository.save(car);
    }

    @Override
    public void delete(int id) {
        this.carBusinessRules.checkIfCarIdNotExists(id);

        this.carRepository.deleteById(id);
    }
}