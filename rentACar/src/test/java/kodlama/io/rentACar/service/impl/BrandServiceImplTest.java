package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.BrandDtoConverter;
import kodlama.io.rentACar.dto.request.CreateBrandRequest;
import kodlama.io.rentACar.dto.request.UpdateBrandRequest;
import kodlama.io.rentACar.dto.response.BrandDto;
import kodlama.io.rentACar.exception.BrandNameExistsException;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.repository.BrandRepository;
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

class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BrandBusinessRules brandBusinessRules;
    @Mock
    private BrandDtoConverter brandDtoConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_whenGetAllBrandCalled_shouldReturnListOfBrands() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = Brand.builder().build();
        brands.add(brand1);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = BrandDto.builder().build();
        brandDtos.add(brandDto1);

        when(brandRepository.findAll()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);

        List<BrandDto> result = brandService.getAll();

        verify(brandRepository).findAll();
        verify(brandDtoConverter).convertToDto(brand1);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetAllByOrderByNameDesc_whenGetAllBrandCalled_shouldReturnListOfBrands() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = Brand.builder().build();
        brands.add(brand1);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = BrandDto.builder().build();
        brandDtos.add(brandDto1);

        when(brandRepository.findAllByOrderByNameDesc()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);

        List<BrandDto> result = brandService.getAllByOrderByNameDesc();

        verify(brandRepository).findAllByOrderByNameDesc();
        verify(brandDtoConverter).convertToDto(brand1);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetAllByOrderByNameAsc_whenGetAllBrandCalled_shouldReturnListOfBrandDtos() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = Brand.builder().build();
        brands.add(brand1);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = BrandDto.builder().build();
        brandDtos.add(brandDto1);

        when(brandRepository.findAllByOrderByNameAsc()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);

        List<BrandDto> result = brandService.getAllByOrderByNameAsc();

        verify(brandRepository).findAllByOrderByNameAsc();
        verify(brandDtoConverter).convertToDto(brand1);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetById_whenBrandIdExists_shouldReturnBrandDto() {

        Long id = 1L;

        Brand brand = Brand.builder().id(id).build();
        BrandDto brandDto = BrandDto.builder().id(id).build();

        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
        when(brandDtoConverter.convertToDto(brand)).thenReturn(brandDto);

        BrandDto result = brandService.getById(id);

        verify(brandRepository).findById(id);
        verify(brandDtoConverter).convertToDto(brand);

        assertEquals(result, brandDto);
    }

    @Test
    public void testGetById_whenBrandIdDoesNotExist_shouldThrowBrandNotFoundException() {

        Long id = 1L;

        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.getById(id));

        verifyNoInteractions(brandDtoConverter);
    }

    @Test
    public void testCreate_whenCreateBrandCalledWithRequest_shouldReturnBrandDto() {
        CreateBrandRequest request = CreateBrandRequest.builder().name("brand").build();

        Brand brand = Brand.builder().name(request.getName()).build();

        BrandDto brandDto = BrandDto.builder().name(brand.getName()).build();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(brand);
        when(brandDtoConverter.convertToDto(brand)).thenReturn(brandDto);

        BrandDto result = brandService.create(request);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Brand.class);
        verify(brandRepository).save(brand);
        verify(brandDtoConverter).convertToDto(brand);

        assertEquals(result, brandDto);
    }

    @Test
    public void testCreate_whenBrandNameExists_shouldThrowBrandNameExistsException() {
        CreateBrandRequest request = CreateBrandRequest.builder().name("brand").build();

        doThrow(BrandNameExistsException.class).when(brandBusinessRules)
                .checkIfBrandNameExists(request.getName());

        assertThrows(BrandNameExistsException.class, () -> brandService.create(request));

        verify(brandBusinessRules).checkIfBrandNameExists(request.getName());
        verifyNoInteractions(modelMapperService, brandRepository, brandDtoConverter);
    }

    @Test
    public void testUpdate_whenUpdateBrandCalledWithRequest_shouldReturnBrandDto() {
        UpdateBrandRequest request = UpdateBrandRequest.builder().id(1L).name("brand").build();

        Brand brand = Brand.builder().name(request.getName()).build();

        BrandDto brandDto = BrandDto.builder().name(brand.getName()).build();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(request, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(brand);
        when(brandDtoConverter.convertToDto(brand)).thenReturn(brandDto);

        BrandDto result = brandService.update(request);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(request, Brand.class);
        verify(brandRepository).save(brand);
        verify(brandDtoConverter).convertToDto(brand);

        assertEquals(result, brandDto);
    }

    @Test
    public void testUpdate_whenBrandIdDoesNotExists_shouldThrowBrandNotFoundException() {
        UpdateBrandRequest request = UpdateBrandRequest.builder().id(1L).build();

        doThrow(BrandNotFoundException.class).when(brandBusinessRules)
                .checkIfBrandIdNotExists(request.getId());

        assertThrows(BrandNotFoundException.class, () -> brandService.update(request));

        verify(brandBusinessRules).checkIfBrandIdNotExists(request.getId());
        verifyNoInteractions(modelMapperService, brandRepository, brandDtoConverter);
    }

    @Test
    public void testUpdate_whenBrandNameExists_shouldThrowBrandNameExistsException() {
        UpdateBrandRequest request = UpdateBrandRequest.builder().id(1L).build();

        doThrow(BrandNameExistsException.class).when(brandBusinessRules)
                .checkIfBrandNameExists(request.getName());

        assertThrows(BrandNameExistsException.class, () -> brandService.update(request));

        verify(brandBusinessRules).checkIfBrandNameExists(request.getName());
        verifyNoInteractions(modelMapperService, brandRepository, brandDtoConverter);
    }

    @Test
    public void testDelete_whenBrandIdExists_shouldDeleteBrand() {

        Long id = 1L;

        brandService.delete(id);

        verify(brandBusinessRules).checkIfBrandIdNotExists(id);
        verify(brandRepository).deleteById(id);
    }

    @Test
    public void testDelete_whenBrandIdDoesNotExists_shouldThrowBrandNotFoundException() {

        Long id = 1L;

        doThrow(BrandNotFoundException.class).when(brandBusinessRules).checkIfBrandIdNotExists(id);

        assertThrows(BrandNotFoundException.class, () -> brandService.delete(id));

        verify(brandBusinessRules).checkIfBrandIdNotExists(id);
        verifyNoInteractions(brandRepository);
    }
}