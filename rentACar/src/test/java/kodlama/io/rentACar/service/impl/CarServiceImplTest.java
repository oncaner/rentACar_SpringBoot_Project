package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.CarBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.CarDtoConverter;
import kodlama.io.rentACar.dto.request.CreateCarRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByDailyPriceRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByModelYearRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByPlateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarByStateRequest;
import kodlama.io.rentACar.dto.request.UpdateCarRequest;
import kodlama.io.rentACar.dto.response.CarDto;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.exception.CarPlateExistsException;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private ModelService modelService;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CarBusinessRules carBusinessRules;
    @Mock
    private ModelBusinessRules modelBusinessRules;
    @Mock
    private BrandBusinessRules brandBusinessRules;
    @Mock
    private CarDtoConverter carDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = Car.builder().build();
        List<Car> cars = new ArrayList<>();
        cars.add(car1);

        CarDto carDto1 = CarDto.builder().build();
        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        List<CarDto> result = carService.getAll();

        verify(carRepository).findAll();
        verify(carDtoConverter).convertToDto(car1);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByOrderByDailyPriceAsc_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = Car.builder().build();
        List<Car> cars = new ArrayList<>();
        cars.add(car1);

        CarDto carDto1 = CarDto.builder().build();
        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        when(carRepository.findAllByOrderByDailyPriceAsc()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        List<CarDto> result = carService.getAllByOrderByDailyPriceAsc();

        verify(carRepository).findAllByOrderByDailyPriceAsc();
        verify(carDtoConverter).convertToDto(car1);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByOrderByDailyPriceDesc_whenGetAllCarsCalled_shouldReturnListOfCarDto() {

        Car car1 = Car.builder().build();
        List<Car> cars = new ArrayList<>();
        cars.add(car1);

        CarDto carDto1 = CarDto.builder().build();
        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        when(carRepository.findAllByOrderByDailyPriceDesc()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        List<CarDto> result = carService.getAllByOrderByDailyPriceDesc();

        verify(carRepository).findAllByOrderByDailyPriceDesc();
        verify(carDtoConverter).convertToDto(car1);

        assertEquals(result, carDtos);
    }

    @Test
    public void testGetAllByModelId_whenModelIdExists_shouldReturnListOfCarDto() {

        Long id = 1L;

        Car car1 = Car.builder().build();
        List<Car> cars = new ArrayList<>();
        cars.add(car1);

        CarDto carDto1 = CarDto.builder().build();
        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        when(carRepository.findAllByModelId(id)).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        List<CarDto> result = carService.getAllByModelId(id);

        verify(carRepository).findAllByModelId(id);
        verify(carDtoConverter).convertToDto(car1);

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

        Car car1 = Car.builder().build();
        List<Car> cars = new ArrayList<>();
        cars.add(car1);

        CarDto carDto1 = CarDto.builder().build();
        List<CarDto> carDtos = new ArrayList<>();
        carDtos.add(carDto1);

        when(carRepository.findAll()).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        when(carRepository.findAllByBrandId(id)).thenReturn(cars);
        when(carDtoConverter.convertToDto(car1)).thenReturn(carDto1);

        List<CarDto> result = carService.getAllByBrandId(id);

        verify(carRepository).findAllByBrandId(id);
        verify(carDtoConverter).convertToDto(car1);

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

        Car car = Car.builder().build();

        CarDto carDto = CarDto.builder().build();

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

        Car car = Car.builder().id(id).build();

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

        CreateCarRequest request = CreateCarRequest.builder()
                .plate("123").dailyPrice(123).modelYear(123).state(1).modelId(1L).build();

        Car car = Car.builder()
                .id(1L).plate(request.getPlate()).dailyPrice(request.getDailyPrice())
                .modelYear(request.getModelYear()).state(request.getState())
                .model(Model.builder().name("model")
                        .brand(Brand.builder().name("brand").build()).build()).build();

        CarDto carDto = CarDto.builder().id(car.getId()).plate(car.getPlate()).dailyPrice(car.getDailyPrice()).modelYear(car.getModelYear()).state(car.getState()).modelName(car.getModel().getName()).brandName(car.getModel().getBrand().getName()).build();

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

        CreateCarRequest request = CreateCarRequest.builder().plate("123").build();

        doThrow(CarPlateExistsException.class).when(carBusinessRules)
                .checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.create(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());
        verifyNoInteractions(modelMapper, modelMapperService, carDtoConverter, carRepository);
    }

    @Test
    public void testCreate_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        CreateCarRequest request = CreateCarRequest.builder().modelId(1L).build();

        doThrow(ModelNotFoundException.class).when(modelBusinessRules)
                .checkIfModelIdNotExists(request.getModelId());

        assertThrows(ModelNotFoundException.class, () -> carService.create(request));

        verify(modelBusinessRules).checkIfModelIdNotExists(request.getModelId());
        verifyNoInteractions(modelMapper, modelMapperService, carDtoConverter, carRepository);
    }

    @Test
    public void testUpdate_whenCarCalledWithRequest_shouldReturnCarDto() {

        UpdateCarRequest request = UpdateCarRequest.builder()
                .id(1L).plate("123").dailyPrice(123).modelYear(123).state(1).build();

        Car oldCar = Car.builder()
                .id(request.getId()).plate("123").dailyPrice(123).modelYear(123).state(1)
                .model(Model.builder().name("model")
                        .brand(Brand.builder().name("brand").build()).build()).build();

        Car updatedCar = Car.builder()
                .id(request.getId()).plate(request.getPlate()).dailyPrice(request.getDailyPrice())
                .modelYear(request.getModelYear()).state(request.getState())
                .model(oldCar.getModel()).build();

        CarDto carDto = CarDto.builder()
                .id(updatedCar.getId()).plate(updatedCar.getPlate()).dailyPrice(updatedCar.getDailyPrice())
                .modelYear(updatedCar.getModelYear()).state(updatedCar.getState())
                .modelName(updatedCar.getModel().getName())
                .brandName(updatedCar.getModel().getBrand().getName()).build();

        when(carRepository.findById(request.getId())).thenReturn(Optional.of(oldCar));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(modelService.getById(oldCar.getModel().getId()), Model.class)).thenReturn(oldCar.getModel());
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

        UpdateCarRequest request = UpdateCarRequest.builder().id(1L).build();

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.update(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());
        verifyNoInteractions(carRepository, modelMapperService, modelMapper, carDtoConverter);
    }

    @Test
    public void testUpdate_whenCarPlateExists_shouldThrowCarPlateExistsException() {

        UpdateCarRequest request = UpdateCarRequest.builder().plate("123").build();

        doThrow(CarPlateExistsException.class).when(carBusinessRules).checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.update(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());
        verifyNoInteractions(carRepository, modelMapperService, modelMapper, carDtoConverter);
    }

    @Test
    public void testUpdateByPlate_whenUpdateByPlateCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByPlateRequest request = UpdateCarByPlateRequest.builder().id(1L).plate("123").build();

        Car car = Car.builder().id(request.getId()).plate(request.getPlate()).build();

        CarDto carDto = CarDto.builder().id(car.getId()).plate(car.getPlate()).build();

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

        UpdateCarByPlateRequest request = UpdateCarByPlateRequest.builder().id(1L).build();

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByPlate(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByPlate_whenPlateExists_shouldThrowCarPlateExistsException() {

        UpdateCarByPlateRequest request = UpdateCarByPlateRequest.builder().plate("123").build();

        doThrow(CarPlateExistsException.class).when(carBusinessRules).checkIfPlateExists(request.getPlate());

        assertThrows(CarPlateExistsException.class, () -> carService.updateByPlate(request));

        verify(carBusinessRules).checkIfPlateExists(request.getPlate());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByDailyPrice_whenUpdateByDailyPriceCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByDailyPriceRequest request = UpdateCarByDailyPriceRequest.builder()
                .id(1L).dailyPrice(123).build();

        Car car = Car.builder().id(request.getId()).dailyPrice(request.getDailyPrice()).build();

        CarDto carDto = CarDto.builder().id(car.getId()).dailyPrice(car.getDailyPrice()).build();

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

        UpdateCarByDailyPriceRequest request = UpdateCarByDailyPriceRequest.builder().id(1L).build();

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByDailyPrice(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByModelYear_whenUpdateByModelYearCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByModelYearRequest request = UpdateCarByModelYearRequest.builder()
                .id(1L).modelYear(123).build();

        Car car = Car.builder().id(request.getId()).modelYear(request.getModelYear()).build();

        CarDto carDto = CarDto.builder().id(car.getId()).modelYear(car.getModelYear()).build();

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

        UpdateCarByModelYearRequest request = UpdateCarByModelYearRequest.builder().id(1L).build();

        doThrow(CarNotFoundException.class).when(carBusinessRules).checkIfCarIdNotExists(request.getId());

        assertThrows(CarNotFoundException.class, () -> carService.updateByModelYear(request));

        verify(carBusinessRules).checkIfCarIdNotExists(request.getId());

        verifyNoInteractions(carRepository, carDtoConverter);
    }

    @Test
    public void testUpdateByState_whenUpdateByStateCalledWithRequest_shouldReturnCarDto() {

        UpdateCarByStateRequest request = UpdateCarByStateRequest.builder().id(1L).state(123).build();

        Car car = Car.builder().id(request.getId()).state(request.getState()).build();

        CarDto carDto = CarDto.builder().id(car.getId()).state(car.getState()).build();

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

        UpdateCarByStateRequest request = UpdateCarByStateRequest.builder().id(1L).build();

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