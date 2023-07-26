package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.ModelDtoConverter;
import kodlama.io.rentACar.dto.request.CreateModelRequest;
import kodlama.io.rentACar.dto.request.UpdateModelRequest;
import kodlama.io.rentACar.dto.response.ModelDto;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.exception.ModelNameExistsException;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.ModelRepository;
import kodlama.io.rentACar.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

class ModelServiceImplTest {

    @InjectMocks
    private ModelServiceImpl modelService;
    @Mock
    private BrandService brandService;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ModelRepository modelRepository;
    @Mock
    private ModelBusinessRules modelBusinessRules;
    @Mock
    private BrandBusinessRules brandBusinessRules;
    @Mock
    private ModelDtoConverter modelDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = Model.builder().build();
        models.add(model1);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = ModelDto.builder().build();
        modelDtos.add(modelDto1);

        when(modelRepository.findAll()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);

        List<ModelDto> result = modelService.getAll();

        verify(modelRepository).findAll();
        verify(modelDtoConverter).convertToDto(model1);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetAllByOrderByNameAsc_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = Model.builder().build();
        models.add(model1);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = ModelDto.builder().build();
        modelDtos.add(modelDto1);

        when(modelRepository.findAllByOrderByNameAsc()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);

        List<ModelDto> result = modelService.getAllByOrderByNameAsc();

        verify(modelRepository).findAllByOrderByNameAsc();
        verify(modelDtoConverter).convertToDto(model1);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetAllByOrderByNameDesc_whenGetAllModelCalled_shouldReturnListOfModelDto() {
        List<Model> models = new ArrayList<>();
        Model model1 = Model.builder().build();
        models.add(model1);

        List<ModelDto> modelDtos = new ArrayList<>();
        ModelDto modelDto1 = ModelDto.builder().build();
        modelDtos.add(modelDto1);

        when(modelRepository.findAllByOrderByNameDesc()).thenReturn(models);
        when(modelDtoConverter.convertToDto(model1)).thenReturn(modelDto1);

        List<ModelDto> result = modelService.getAllByOrderByNameDesc();

        verify(modelRepository).findAllByOrderByNameDesc();
        verify(modelDtoConverter).convertToDto(model1);

        assertEquals(result, modelDtos);
    }

    @Test
    public void testGetById_whenModelIdExists_shouldReturnModelDto() {

        Long id = 1L;

        Model model = Model.builder().id(id).build();
        ModelDto modelDto = ModelDto.builder().id(id).build();

        when(modelRepository.findById(id)).thenReturn(Optional.of(model));
        when(modelDtoConverter.convertToDto(model)).thenReturn(modelDto);

        ModelDto result = modelService.getById(id);

        verify(modelRepository).findById(id);
        verify(modelDtoConverter).convertToDto(model);

        assertEquals(result, modelDto);
    }

    @Test
    public void testGetById_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        Long id = 1L;

        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> modelService.getById(id));

        verifyNoInteractions(modelDtoConverter);
    }

    @Test
    public void testCreate_whenCreateModelCalledWithRequest_shouldReturnModelDto() {

        Long id = 1L;

        CreateModelRequest request = CreateModelRequest.builder()
                .name("model").brandId(1L).build();

        Model model = Model.builder().id(id).name(request.getName())
                .brand(Brand.builder().id(request.getBrandId()).name("brand").build()).build();

        ModelDto modelDto = ModelDto
                .builder().id(model.getId()).name(model.getName())
                .brandName(model.getBrand().getName()).build();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Model.class)).thenReturn(model);
        when(modelRepository.save(model)).thenReturn(model);
        when(modelDtoConverter.convertToDto(model)).thenReturn(modelDto);

        ModelDto result = modelService.create(request);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Model.class);
        verify(modelRepository).save(model);
        verify(modelDtoConverter).convertToDto(model);

        assertEquals(result, modelDto);
    }

    @Test
    public void testCreate_whenModelNameExists_shouldThrowModelNameExistsException() {

        CreateModelRequest request = CreateModelRequest.builder().name("model").build();

        doThrow(ModelNameExistsException.class).when(modelBusinessRules)
                .checkIfModelNameExists(request.getName());

        assertThrows(ModelNameExistsException.class, () -> modelService.create(request));

        verify(modelBusinessRules).checkIfModelNameExists(request.getName());
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);
    }

    @Test
    public void testCreate_whenBrandIdDoesNotExists_shouldThrowModelNotFoundException() {

        Long brandId = 1L;

        CreateModelRequest request = CreateModelRequest.builder().brandId(brandId).build();

        doThrow(BrandNotFoundException.class).when(brandBusinessRules)
                .checkIfBrandIdNotExists(request.getBrandId());

        assertThrows(BrandNotFoundException.class, () -> modelService.create(request));

        verify(brandBusinessRules).checkIfBrandIdNotExists(request.getBrandId());
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);
    }

    @Test
    public void testUpdate_whenUpdateModelCalledWithRequest_shouldReturnModelDto() {

        Long id = 1L;

        UpdateModelRequest request = UpdateModelRequest.builder().id(id).name("model").build();

        Model model = Model.builder().id(request.getId()).name(request.getName())
                .brand(Brand.builder().id(id).name("brand").build()).build();

        ModelDto modelDto = ModelDto.builder()
                .id(model.getId()).name(model.getName()).brandName(model.getBrand().getName()).build();

        when(modelRepository.findById(request.getId())).thenReturn(Optional.of(model));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(brandService.getById(model.getId()), Brand.class))
                .thenReturn(model.getBrand());
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Model.class)).thenReturn(model);
        when(modelRepository.save(model)).thenReturn(model);
        when(modelDtoConverter.convertToDto(model)).thenReturn(modelDto);

        ModelDto result = modelService.update(request);

        verify(modelRepository).findById(request.getId());
        verify(modelMapperService).forResponse();
        verify(modelMapper).map(brandService.getById(model.getBrand().getId()), Brand.class);
        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Model.class);
        verify(modelRepository).save(model);
        verify(modelDtoConverter).convertToDto(model);

        assertEquals(result, modelDto);
    }

    @Test
    public void testUpdate_whenModelNameExists_shouldThrowModelNameExistsException() {

        UpdateModelRequest request = UpdateModelRequest.builder().name("model").build();

        doThrow(ModelNameExistsException.class).when(modelBusinessRules)
                .checkIfModelNameExists(request.getName());

        assertThrows(ModelNameExistsException.class, () -> modelService.update(request));

        verify(modelBusinessRules).checkIfModelNameExists(request.getName());
        verifyNoInteractions(modelMapperService, modelMapper, modelDtoConverter, modelRepository);
    }

    @Test
    public void testUpdate_whenModelIdDoesNotExist_shouldThrowModelNotFoundException() {

        UpdateModelRequest request = UpdateModelRequest.builder().id(1L).build();

        doThrow(ModelNotFoundException.class).when(modelBusinessRules)
                .checkIfModelIdNotExists(request.getId());

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