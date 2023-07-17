package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.CarBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.CarDto;
import kodlama.io.rentACar.dto.CarDtoConverter;
import kodlama.io.rentACar.dto.request.CreateCarRequest;
import kodlama.io.rentACar.dto.request.UpdateCarRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.exception.CarPlateExistsException;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.service.CarService;
import kodlama.io.rentACar.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.doThrow;

class CarServiceImplTest {

    private CarService carService;
    private CarRepository carRepository;
    private ModelService modelService;
    private ModelMapperService modelMapperService;
    private ModelMapper modelMapper;
    private CarBusinessRules carBusinessRules;
    private ModelBusinessRules modelBusinessRules;
    private BrandBusinessRules brandBusinessRules;
    private CarDtoConverter carDtoConverter;

    @BeforeEach
    void setUp() {

        carRepository = mock(CarRepository.class);
        modelService = mock(ModelService.class);
        modelMapperService = mock(ModelMapperService.class);
        modelMapper = mock(ModelMapper.class);
        carBusinessRules = mock(CarBusinessRules.class);
        modelBusinessRules = mock(ModelBusinessRules.class);
        brandBusinessRules = mock(BrandBusinessRules.class);
        carDtoConverter = mock(CarDtoConverter.class);

        carService = new CarServiceImpl(carRepository, modelService, modelMapperService, carBusinessRules, modelBusinessRules, brandBusinessRules, carDtoConverter);
    }

    @Test
    public void testGetAll_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = new Car(1L, "plate1", 1, 1, 1, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        Car car2 = new Car(2L, "plate2", 2, 2, 2, new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of()), List.of());

        CarDto carDto1 = new CarDto(1L, "plate1", 1, 1, 1, "model1", "brand1");

        CarDto carDto2 = new CarDto(2L, "plate2", 2, 2, 2, "model2", "brand2");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);
        carDtos.add(carDto2);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);
        when(carDtoConverter.convertToDto(car2)).thenReturn(carDto2);

        List<CarDto> result = carService.getAll();

        verify(carRepository).findAll();
        verify(carDtoConverter).convertToDto(car1);
        verify(carDtoConverter).convertToDto(car2);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByOrderByDailyPriceAsc_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = new Car(1L, "plate1", 1, 1, 1, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        Car car2 = new Car(2L, "plate2", 2, 2, 2, new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of()), List.of());

        CarDto carDto1 = new CarDto(1L, "plate1", 1, 1, 1, "model1", "brand1");

        CarDto carDto2 = new CarDto(2L, "plate2", 2, 2, 2, "model2", "brand2");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);
        carDtos.add(carDto2);

        when(carRepository.findAllByOrderByDailyPriceAsc()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);
        when(carDtoConverter.convertToDto(car2)).thenReturn(carDto2);

        List<CarDto> result = carService.getAllByOrderByDailyPriceAsc();

        verify(carRepository).findAllByOrderByDailyPriceAsc();
        verify(carDtoConverter).convertToDto(car1);
        verify(carDtoConverter).convertToDto(car2);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByOrderByDailyPriceDesc_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = new Car(1L, "plate1", 1, 1, 1, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        Car car2 = new Car(2L, "plate2", 2, 2, 2, new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of()), List.of());

        CarDto carDto1 = new CarDto(1L, "plate1", 1, 1, 1, "model1", "brand1");

        CarDto carDto2 = new CarDto(2L, "plate2", 2, 2, 2, "model2", "brand2");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);
        carDtos.add(carDto2);

        when(carRepository.findAllByOrderByDailyPriceDesc()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);
        when(carDtoConverter.convertToDto(car2)).thenReturn(carDto2);

        List<CarDto> result = carService.getAllByOrderByDailyPriceDesc();

        verify(carRepository).findAllByOrderByDailyPriceDesc();
        verify(carDtoConverter).convertToDto(car1);
        verify(carDtoConverter).convertToDto(car2);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByModelId_whenModelIdExists_shouldReturnListOfCarDto() {

        Long id = 1L;

        Car car1 = new Car(1L, "plate1", 1, 1, 1, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        Car car2 = new Car(2L, "plate2", 2, 2, 2, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        CarDto carDto1 = new CarDto(1L, "plate1", 1, 1, 1, "model1", "brand1");

        CarDto carDto2 = new CarDto(2L, "plate2", 2, 2, 2, "model1", "brand1");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);
        carDtos.add(carDto2);

        when(carRepository.findAllByModelId(id)).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);
        when(carDtoConverter.convertToDto(car2)).thenReturn(carDto2);

        List<CarDto> result = carService.getAllByModelId(id);

        verify(carRepository).findAllByModelId(id);
        verify(carDtoConverter).convertToDto(car1);
        verify(carDtoConverter).convertToDto(car2);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByModelId_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        Long id = 1L;

        doThrow(ModelNotFoundException.class).when(modelBusinessRules).checkIfModelIdNotExists(id);

        assertThrows(ModelNotFoundException.class, () -> carService.getAllByModelId(id));

        verify(modelBusinessRules).checkIfModelIdNotExists(id);
        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testGetAllByBrandId_whenBrandIdExists_shouldReturnListOfCarDto() {

        Long id = 1L;

        Car car1 = new Car(1L, "plate1", 1, 1, 1, new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        Car car2 = new Car(2L, "plate2", 2, 2, 2, new Model(2L, "model2", new Brand(1L, "brand1", List.of()), List.of()), List.of());

        CarDto carDto1 = new CarDto(1L, "plate1", 1, 1, 1, "model1", "brand1");

        CarDto carDto2 = new CarDto(2L, "plate2", 2, 2, 2, "model2", "brand1");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);
        carDtos.add(carDto2);

        when(carRepository.findAllByBrandId(id)).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);
        when(carDtoConverter.convertToDto(car2)).thenReturn(carDto2);

        List<CarDto> result = carService.getAllByBrandId(id);

        verify(carRepository).findAllByBrandId(id);
        verify(carDtoConverter).convertToDto(car1);
        verify(carDtoConverter).convertToDto(car2);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByBrandId_whenBrandIdDoesNotExist_shouldThrowBrandNotFoundException() {

        Long id = 1L;

        doThrow(BrandNotFoundException.class).when(brandBusinessRules).checkIfBrandIdNotExists(id);

        assertThrows(BrandNotFoundException.class, () -> carService.getAllByBrandId(id));

        verify(brandBusinessRules).checkIfBrandIdNotExists(id);
        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testGetById_whenCarIdExists_shouldReturnCarDto() {

        Long id = 1L;

        Car car = new Car(1L, "plate", 1, 1, 1, new Model(1L, "model", new Brand(1L, "brand", List.of()), List.of()), List.of());

        CarDto carDto = new CarDto(1L, "plate", 1, 1, 1, "model", "brand");

        when(carRepository.findById(id)).thenReturn(Optional.of(car));
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.getById(id);

        verify(carRepository).findById(id);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testGetById_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        Long id = 1L;

        when(carRepository.findById(id)).thenReturn(Optional.empty());

        Mockito.doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(id);

        assertThrows(CarNotFoundException.class, () -> carService.getById(id));

        verifyNoInteractions(carDtoConverter);
    }

    @Test
    public void testGetByIdForOtherService_whenCarIdExists_shouldReturnCar() {

        Long id = 1L;

        Brand brand = new Brand(1L, "brand", List.of());
        Model model = new Model(1L, "model", brand, List.of());
        Car car = new Car(1L, "plate", 1, 1, 1, model, List.of());

        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        Car result = carService.getByIdForOtherService(id);

        assertEquals(result, car);

        verify(carRepository).findById(id);
    }

    @Test
    public void testGetByIdForOtherService_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        Long id = 1L;

        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.getByIdForOtherService(id));

        verify(carRepository).findById(id);
    }

    @Test
    public void testCreate_whenCreateCarCalledWithRequest_shouldReturnCarDto() {

        CreateCarRequest request = new CreateCarRequest("plate", 1, 1, 1, 1L);

        Model model = new Model(1L, "model", new Brand(1L, "brand", List.of()), List.of());

        Car car = new Car(1L, request.getPlate(), request.getDailyPrice(), request.getModelYear(), request.getState(), model, List.of());

        CarDto carDto = new CarDto(1L, "plate", 1, 1, 1, model.getName(), model.getBrand().getName());

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.create(request);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Car.class);
        verify(carRepository).save(car);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testCreate_whenPlateExists_shouldThrowCarPlateExistsException() {

        CreateCarRequest request = new CreateCarRequest("plate", 1, 1, 1, 1L);

        doThrow(CarPlateExistsException.class).when(carBusinessRules).checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.create(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());
        verifyNoInteractions(modelMapper, modelMapperService, carDtoConverter, carRepository);
    }

    @Test
    public void testCreate_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        CreateCarRequest request = new CreateCarRequest("plate", 1, 1, 1, 1L);

        doThrow(ModelNotFoundException.class).when(modelBusinessRules).checkIfModelIdNotExists(request.getModelId());

        assertThrows(ModelNotFoundException.class, () -> carService.create(request));

        verify(modelBusinessRules).checkIfModelIdNotExists(request.getModelId());
        verifyNoInteractions(modelMapper, modelMapperService, carDtoConverter, carRepository);
    }

    @Test
    public void testUpdate_whenCarCalledWithRequest_shouldReturnCarDto() {

        UpdateCarRequest request = new UpdateCarRequest(1L, "newPlate", 2, 2, 2);

        Brand brand = new Brand(1L, "brand", List.of());
        Model model = new Model(1L, "model", brand, List.of());

        Car oldCar = new Car(1L, "plate", 1, 1, 1, model, List.of());

        Car updatedCar = new Car(request.getId(), request.getPlate(), request.getDailyPrice(), request.getModelYear(), request.getState(), oldCar.getModel(), List.of());

        CarDto carDto = new CarDto(updatedCar.getId(), updatedCar.getPlate(), updatedCar.getDailyPrice(), updatedCar.getModelYear(), updatedCar.getState(), updatedCar.getModel().getName(), updatedCar.getModel().getBrand().getName());

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(oldCar));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(modelService.getById(oldCar.getModel().getId()), Model.class)).thenReturn(model);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Car.class)).thenReturn(updatedCar);
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        when(carDtoConverter.convertToDto(updatedCar)).thenReturn(carDto);

        CarDto result = carService.update(request);

        verify(carRepository).findById(request.getId());
        verify(modelMapperService).forResponse();
        verify(modelMapper).map(modelService.getById(oldCar.getModel().getId()), Model.class);
        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Car.class);
        verify(carRepository).save(updatedCar);
        verify(carDtoConverter).convertToDto(updatedCar);

        assertEquals(result, carDto);
    }

    @Test
    public void testUpdate_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        UpdateCarRequest request = new UpdateCarRequest(1L, "plate", 1, 1, 1);

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.update(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());
        verifyNoInteractions(carRepository, modelMapperService, modelMapper, carDtoConverter);
    }

    @Test
    public void testUpdate_whenCarPlateExists_shouldThrowCarPlateExistsException() {

        UpdateCarRequest request = new UpdateCarRequest(1L, "plate", 1, 1, 1);

        doThrow(CarPlateExistsException.class).when(carBusinessRules).checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.update(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());
        verifyNoInteractions(carRepository, modelMapperService, modelMapper, carDtoConverter);
    }

    @Test
    public void testUpdateByPlate_whenUpdateByPlateCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByPlateRequest request = new UpdateCarByPlateRequest(1L, "XYZ123");

        Car car = new Car();
        car.setId(request.getId());
        car.setPlate(request.getPlate());

        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setPlate(car.getPlate());

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.updateByPlate(request);

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());
        verify(carBusinessRules).checkIfPlateExists(request.getPlate());
        verify(carRepository).findById(request.getId());
        verify(carRepository).save(car);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testUpdateByPlate_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        UpdateCarByPlateRequest request = new UpdateCarByPlateRequest(1L, "plate");

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByPlate(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByPlate_whenPlateExists_shouldThrowCarPlateExistsException() {

        UpdateCarByPlateRequest request = new UpdateCarByPlateRequest(1L, "plate");

        doThrow(CarPlateExistsException.class).when(carBusinessRules).checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.updateByPlate(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByDailyPrice_whenUpdateByDailyPriceCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByDailyPriceRequest request = new UpdateCarByDailyPriceRequest(1L, 1);

        Car car = new Car();
        car.setId(request.getId());
        car.setDailyPrice(request.getDailyPrice());

        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setDailyPrice(car.getDailyPrice());

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.updateByDailyPrice(request);

        verify(carRepository).findById(request.getId());
        verify(carRepository).save(car);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testUpdateByDailyPrice_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        UpdateCarByDailyPriceRequest request = new UpdateCarByDailyPriceRequest(1L, 1);

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByDailyPrice(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByModelYear_whenUpdateByModelYearCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByModelYearRequest request = new UpdateCarByModelYearRequest(1L, 1);

        Car car = new Car();
        car.setId(request.getId());
        car.setModelYear(request.getModelYear());

        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setModelYear(car.getModelYear());

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.updateByModelYear(request);

        verify(carRepository).findById(request.getId());
        verify(carRepository).save(car);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testUpdateByModelYear_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        UpdateCarByModelYearRequest request = new UpdateCarByModelYearRequest(1L, 1);

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByModelYear(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByState_whenUpdateByStateCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByStateRequest request = new UpdateCarByStateRequest(1L, 1);

        Car car = new Car();
        car.setId(request.getId());
        car.setState(request.getState());

        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setState(car.getState());

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);
        when(carDtoConverter.convertToDto(car)).thenReturn(carDto);

        CarDto result = carService.updateByState(request);

        verify(carRepository).findById(request.getId());
        verify(carRepository).save(car);
        verify(carDtoConverter).convertToDto(car);

        assertEquals(result, carDto);
    }

    @Test
    public void testUpdateByState_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        UpdateCarByStateRequest request = new UpdateCarByStateRequest(1L, 1);

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByState(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testDelete_whenCarIdExists_shouldDeleteCar() {

        Long id = 1L;

        carService.delete(id);

        verify(carBusinessRules).checkIfCarIdNotExists(id);
        verify(carRepository).deleteById(id);
    }

    @Test
    public void testDelete_whenCarIdDoesNotExist_shouldThrowCarNotFoundException() {

        Long id = 1L;

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(id);

        assertThrows(CarNotFoundException.class, () -> carService.delete(id));

        verify(carBusinessRules).checkIfCarIdNotExists(id);
        verifyNoInteractions(carRepository);
    }

}






