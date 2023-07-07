package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.ModelDto;
import kodlama.io.rentACar.dto.ModelDtoConverter;
import kodlama.io.rentACar.dto.request.CreateModelRequest;
import kodlama.io.rentACar.dto.request.UpdateModelRequest;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.exception.ModelNameExistsException;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.ModelRepository;
import kodlama.io.rentACar.service.BrandService;
import kodlama.io.rentACar.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class ModelServiceImplTest {

    private ModelService modelService;
    private BrandService brandService;
    private ModelMapperService modelMapperService;
    private ModelMapper modelMapper;
    private ModelRepository modelRepository;
    private ModelBusinessRules modelBusinessRules;
    private BrandBusinessRules brandBusinessRules;
    private ModelDtoConverter modelDtoConverter;

    @BeforeEach
    void setUp() {

        brandService = mock(BrandService.class);
        modelMapperService = mock(ModelMapperService.class);
        modelMapper = mock(ModelMapper.class);
        modelRepository = mock(ModelRepository.class);
        modelBusinessRules = mock(ModelBusinessRules.class);
        brandBusinessRules = mock(BrandBusinessRules.class);
        modelDtoConverter = mock(ModelDtoConverter.class);

        modelService = new ModelServiceImpl(modelRepository, brandService, modelMapperService, modelBusinessRules, brandBusinessRules, modelDtoConverter);
    }

    @Test
    public void testGetAll_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of());
        Model model2 = new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of());
        models.add(model1);
        models.add(model2);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = new ModelDto(1L, "model1", "brand1");
        ModelDto modelDto2 = new ModelDto(2L, "model2", "brand2");
        modelDtos.add(modelDto1);
        modelDtos.add(modelDto2);

        when(modelRepository.findAll()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);
        when(modelDtoConverter.convertToDto(model2)).thenReturn(modelDto2);

        List<ModelDto> result = modelService.getAll();

        verify(modelRepository).findAll();
        verify(modelDtoConverter).convertToDto(model1);
        verify(modelDtoConverter).convertToDto(model2);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetAllByOrderByNameAsc_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of());
        Model model2 = new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of());
        models.add(model1);
        models.add(model2);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = new ModelDto(1L, "model1", "brand1");
        ModelDto modelDto2 = new ModelDto(2L, "model2", "brand2");
        modelDtos.add(modelDto1);
        modelDtos.add(modelDto2);

        when(modelRepository.findAllByOrderByNameAsc()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);
        when(modelDtoConverter.convertToDto(model2)).thenReturn(modelDto2);

        List<ModelDto> result = modelService.getAllByOrderByNameAsc();

        verify(modelRepository).findAllByOrderByNameAsc();
        verify(modelDtoConverter).convertToDto(model1);
        verify(modelDtoConverter).convertToDto(model2);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetAllByOrderByNameDesc_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = new Model(1L, "model1", new Brand(1L, "brand1", List.of()), List.of());
        Model model2 = new Model(2L, "model2", new Brand(2L, "brand2", List.of()), List.of());
        models.add(model1);
        models.add(model2);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = new ModelDto(1L, "model1", "brand1");
        ModelDto modelDto2 = new ModelDto(2L, "model2", "brand2");
        modelDtos.add(modelDto1);
        modelDtos.add(modelDto2);

        when(modelRepository.findAllByOrderByNameDesc()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);
        when(modelDtoConverter.convertToDto(model2)).thenReturn(modelDto2);

        List<ModelDto> result = modelService.getAllByOrderByNameDesc();

        verify(modelRepository).findAllByOrderByNameDesc();
        verify(modelDtoConverter).convertToDto(model1);
        verify(modelDtoConverter).convertToDto(model2);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetById_whenModelIdExists_shouldReturnModelDto() {

        Model model = new Model(1L, "model", new Brand(1L, "brand", List.of()), List.of());
        ModelDto modelDto = new ModelDto(1L, "model", "brand");

        when(modelRepository.findById(1L)).thenReturn(Optional.of(model));
        when(modelDtoConverter.convertToDto(model)).thenReturn(modelDto);

        ModelDto result = modelService.getById(1L);

        verify(modelRepository).findById(1L);
        verify(modelDtoConverter).convertToDto(model);

        assertEquals(result, modelDto);

    }

    @Test
    public void testGetById_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        when(modelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> modelService.getById(1L));

        verifyNoInteractions(modelDtoConverter);

    }

    @Test
    public void testCreate_whenCreateModelCalledWithRequest_shouldReturnModelDto() {

        CreateModelRequest createModelRequest = new CreateModelRequest();
        createModelRequest.setName("model");
        createModelRequest.setBrandId(1L);

        Model model = new Model(1L, "model", new Brand(1L, "brand", List.of()), List.of());

        ModelDto modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setName("model");
        modelDto.setBrandName("brand");

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createModelRequest, Model.class)).thenReturn(model);
        when(modelRepository.save(model)).thenReturn(model);
        when(modelDtoConverter.convertToDto(model)).thenReturn(modelDto);

        ModelDto result = modelService.create(createModelRequest);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(createModelRequest, Model.class);
        verify(modelRepository).save(model);
        verify(modelDtoConverter).convertToDto(model);

        assertEquals(result, modelDto);

    }

    @Test
    public void testCreate_whenModelNameExists_shouldThrowModelNameExistsException() {

        CreateModelRequest createModelRequest = new CreateModelRequest("model", 1L);

        doThrow(ModelNameExistsException.class).when(modelBusinessRules).checkIfModelNameExists("model");

        assertThrows(ModelNameExistsException.class, () -> modelService.create(createModelRequest));

        verify(modelBusinessRules).checkIfModelNameExists("model");
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);

    }

    @Test
    public void testCreate_whenBrandIdDoesNotExists_shouldThrowModelNotFoundException() {

        CreateModelRequest createModelRequest = new CreateModelRequest("model", 1L);

        doThrow(BrandNotFoundException.class).when(brandBusinessRules).checkIfBrandIdNotExists(1L);

        assertThrows(BrandNotFoundException.class, () -> modelService.create(createModelRequest));

        verify(brandBusinessRules).checkIfBrandIdNotExists(1L);
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);

    }

    @Test
    public void testUpdate_whenUpdateModelCalledWithRequest_shouldReturnModelDto() {

        UpdateModelRequest updateModelRequest = new UpdateModelRequest(1L, "model");

        Brand brand = new Brand(1L, "brand", List.of());
        Model oldModel = new Model(1L, "oldModel", brand, List.of());
        Model updatedModel = new Model(1L, "model", brand, List.of());
        ModelDto modelDto = new ModelDto(1L, "model", "brand");

        when(modelRepository.findById(1L)).thenReturn(Optional.of(oldModel));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(brandService.getById(brand.getId()), Brand.class)).thenReturn(brand);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateModelRequest, Model.class)).thenReturn(updatedModel);
        when(modelRepository.save(updatedModel)).thenReturn(updatedModel);
        when(modelDtoConverter.convertToDto(updatedModel)).thenReturn(modelDto);

        ModelDto result = modelService.update(updateModelRequest);

        verify(modelRepository).findById(1L);
        verify(modelMapperService).forResponse();
        verify(modelMapper).map(brandService.getById(brand.getId()), Brand.class);
        verify(modelMapperService).forRequest();
        verify(modelMapper).map(updateModelRequest, Model.class);
        verify(modelRepository).save(updatedModel);
        verify(modelDtoConverter).convertToDto(updatedModel);

        assertEquals(result, modelDto);
    }

    @Test
    public void testUpdate_whenModelNameExists_shouldThrowModelNameExistsException() {

        UpdateModelRequest request = new UpdateModelRequest(1L, "model");

        doThrow(ModelNameExistsException.class).when(modelBusinessRules).checkIfModelNameExists(request.getName());

        assertThrows(ModelNameExistsException.class, () -> modelService.update(request));

        verify(modelBusinessRules).checkIfModelNameExists("model");
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);

    }

    @Test
    public void testUpdate_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        UpdateModelRequest request = new UpdateModelRequest(1L, "model");

        doThrow(ModelNotFoundException.class).when(modelBusinessRules).checkIfModelIdNotExists(request.getId());

        assertThrows(ModelNotFoundException.class, () -> modelService.update(request));

        verify(modelBusinessRules).checkIfModelIdNotExists(request.getId());
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);
    }

    @Test
    public void testDelete_whenModelIdExists_shouldDeleteModel() {

        Long id = 1L;

        modelService.delete(id);

        verify(modelBusinessRules).checkIfModelIdNotExists(id);
        verify(modelRepository).deleteById(id);

    }

    @Test
    public void testDelete_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        Long id = 1L;

        doThrow(ModelNotFoundException.class).when(modelBusinessRules).checkIfModelIdNotExists(id);

        assertThrows(ModelNotFoundException.class, () -> modelService.delete(id));

        verify(modelBusinessRules).checkIfModelIdNotExists(id);
        verifyNoInteractions(modelRepository);

    }
}