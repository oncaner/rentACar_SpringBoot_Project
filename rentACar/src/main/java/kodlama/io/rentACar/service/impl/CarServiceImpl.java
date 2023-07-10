package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.CarBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.CarDto;
import kodlama.io.rentACar.dto.CarDtoConverter;
import kodlama.io.rentACar.dto.request.*;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.service.CarService;
import kodlama.io.rentACar.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelService modelService;
    private final ModelMapperService modelMapperService;
    private final CarBusinessRules carBusinessRules;
    private final ModelBusinessRules modelBusinessRules;
    private final BrandBusinessRules brandBusinessRules;
    private final CarDtoConverter carDtoConverter;

    @Override
    public List<CarDto> getAll() {
        List<Car> cars = this.carRepository.findAll();

        return cars.stream().map(this.carDtoConverter::convertToDto).toList();
    }

    @Override
    public List<CarDto> getAllByOrderByDailyPriceAsc() {
        List<Car> cars = this.carRepository.findAllByOrderByDailyPriceAsc();

        return cars.stream().map(this.carDtoConverter::convertToDto).toList();
    }

    @Override
    public List<CarDto> getAllByOrderByDailyPriceDesc() {
        List<Car> cars = this.carRepository.findAllByOrderByDailyPriceDesc();

        return cars.stream().map(this.carDtoConverter::convertToDto).toList();
    }

    @Override
    public List<CarDto> getAllByModelId(Long id) {
        this.modelBusinessRules.checkIfModelIdNotExists(id);

        List<Car> cars = this.carRepository.findAllByModelId(id);

        return cars.stream().map(this.carDtoConverter::convertToDto).toList();
    }

    @Override
    public List<CarDto> getAllByBrandId(Long id) {
        this.brandBusinessRules.checkIfBrandIdNotExists(id);
        this.brandBusinessRules.checkIfCarsNotExistWithBrandId(id);

        List<Car> cars = this.carRepository.findAllByBrandId(id);

        return cars.stream().map(this.carDtoConverter::convertToDto).toList();
    }

    @Override
    public CarDto getById(Long id) {
        this.carBusinessRules.checkIfCarIdNotExists(id);

        Car car = this.carRepository.findById(id).orElseThrow();

        return this.carDtoConverter.convertToDto(car);
    }

    @Override
    public Car getByIdForOtherService(Long id) {

        return this.carRepository.findById(id).orElseThrow(
                () -> new CarNotFoundException("Car not found with: " + id));
    }

    @Override
    public CarDto create(CreateCarRequest createCarRequest) {
        this.carBusinessRules.checkIfPlateExists(createCarRequest.getPlate());
        this.modelBusinessRules.checkIfModelIdNotExists(createCarRequest.getModelId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

        return this.carDtoConverter.convertToDto(this.carRepository.save(car));
    }

    @Override
    public CarDto update(UpdateCarRequest updateCarRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarRequest.getId());
        this.carBusinessRules.checkIfPlateExists(updateCarRequest.getPlate());

        Car oldCar = this.carRepository.findById(updateCarRequest.getId()).orElseThrow();
        Model model = this.modelMapperService.forResponse()
                .map(this.modelService.getById(oldCar.getModel().getId()), Model.class);

        Car updateCar = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        updateCar.setModel(model);

        return this.carDtoConverter.convertToDto(this.carRepository.save(updateCar));
    }

    @Override
    public CarDto updateByPlate(UpdateCarByPlateRequest updateCarByPlateRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByPlateRequest.getId());
        this.carBusinessRules.checkIfPlateExists(updateCarByPlateRequest.getPlate());

        Car car = this.carRepository.findById(updateCarByPlateRequest.getId()).orElseThrow();
        car.setPlate(updateCarByPlateRequest.getPlate());

        return this.carDtoConverter.convertToDto(this.carRepository.save(car));
    }

    @Override
    public CarDto updateByDailyPrice(UpdateCarByDailyPriceRequest updateCarByDailyPriceRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByDailyPriceRequest.getId());

        Car car = carRepository.findById(updateCarByDailyPriceRequest.getId()).orElseThrow();
        car.setDailyPrice(updateCarByDailyPriceRequest.getDailyPrice());

        return this.carDtoConverter.convertToDto(this.carRepository.save(car));
    }

    @Override
    public CarDto updateByModelYear(UpdateCarByModelYearRequest updateCarByModelYearRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByModelYearRequest.getId());

        Car car = this.carRepository.findById(updateCarByModelYearRequest.getId()).orElseThrow();
        car.setModelYear(updateCarByModelYearRequest.getModelYear());

        return this.carDtoConverter.convertToDto(this.carRepository.save(car));
    }

    @Override
    public CarDto updateByState(UpdateCarByStateRequest updateCarByStateRequest) {
        this.carBusinessRules.checkIfCarIdNotExists(updateCarByStateRequest.getId());

        Car car = this.carRepository.findById(updateCarByStateRequest.getId()).orElseThrow();
        car.setState(updateCarByStateRequest.getState());

        return this.carDtoConverter.convertToDto(this.carRepository.save(car));
    }

    @Override
    public void delete(Long id) {
        this.carBusinessRules.checkIfCarIdNotExists(id);

        this.carRepository.deleteById(id);
    }
}
