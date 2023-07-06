package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.BrandDto;
import kodlama.io.rentACar.dto.BrandDtoConverter;
import kodlama.io.rentACar.dto.request.CreateBrandRequest;
import kodlama.io.rentACar.dto.request.UpdateBrandRequest;
import kodlama.io.rentACar.exception.BrandNameExistsException;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.repository.BrandRepository;
import kodlama.io.rentACar.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BrandServiceImplTest {

    private BrandService brandService;
    private BrandRepository brandRepository;
    private ModelMapperService modelMapperService;
    private ModelMapper modelMapper;
    private BrandBusinessRules brandBusinessRules;
    private BrandDtoConverter brandDtoConverter;

    @BeforeEach
    void setUp() {

        brandRepository = mock(BrandRepository.class);
        modelMapperService = mock(ModelMapperService.class);
        modelMapper = mock(ModelMapper.class);
        brandBusinessRules = mock(BrandBusinessRules.class);
        brandDtoConverter = mock(BrandDtoConverter.class);

        brandService = new BrandServiceImpl(brandRepository, modelMapperService, brandBusinessRules, brandDtoConverter);

    }

    @Test
    public void testGetAll_whenGetAllBrandCalled_shouldReturnListOfBrands() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = new Brand(1L, "brand1", List.of());
        Brand brand2 = new Brand(2L, "brand2", List.of());
        brands.add(brand1);
        brands.add(brand2);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = new BrandDto(1L, "brand1");
        BrandDto brandDto2 = new BrandDto(2L, "brand2");
        brandDtos.add(brandDto1);
        brandDtos.add(brandDto2);

        when(brandRepository.findAll()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);
        when(brandDtoConverter.convertToDto(brand2)).thenReturn(brandDto2);

        List<BrandDto> result = brandService.getAll();

        verify(brandRepository).findAll();
        verify(brandDtoConverter).convertToDto(brand1);
        verify(brandDtoConverter).convertToDto(brand2);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetAllByOrderByNameDesc_whenGetAllBrandCalled_shouldReturnListOfBrands() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = new Brand(1L, "brand1", List.of());
        Brand brand2 = new Brand(2L, "brand2", List.of());
        brands.add(brand1);
        brands.add(brand2);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = new BrandDto(1L, "brand1");
        BrandDto brandDto2 = new BrandDto(2L, "brand2");
        brandDtos.add(brandDto1);
        brandDtos.add(brandDto2);

        when(brandRepository.findAllByOrderByNameDesc()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);
        when(brandDtoConverter.convertToDto(brand2)).thenReturn(brandDto2);

        List<BrandDto> result = brandService.getAllByOrderByNameDesc();

        verify(brandRepository).findAllByOrderByNameDesc();
        verify(brandDtoConverter).convertToDto(brand1);
        verify(brandDtoConverter).convertToDto(brand2);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetAllByOrderByNameAsc_whenGetAllBrandCalled_shouldReturnListOfBrandDtos() {
        List<Brand> brands = new ArrayList<>();
        Brand brand1 = new Brand(1L, "brand1", List.of());
        Brand brand2 = new Brand(2L, "brand2", List.of());
        brands.add(brand1);
        brands.add(brand2);

        List<BrandDto> brandDtos = new ArrayList<>();
        BrandDto brandDto1 = new BrandDto(1L, "brand1");
        BrandDto brandDto2 = new BrandDto(2L, "brand2");
        brandDtos.add(brandDto1);
        brandDtos.add(brandDto2);

        when(brandRepository.findAllByOrderByNameAsc()).thenReturn(brands);
        when(brandDtoConverter.convertToDto(brand1)).thenReturn(brandDto1);
        when(brandDtoConverter.convertToDto(brand2)).thenReturn(brandDto2);

        List<BrandDto> result = brandService.getAllByOrderByNameAsc();

        verify(brandRepository).findAllByOrderByNameAsc();
        verify(brandDtoConverter).convertToDto(brand1);
        verify(brandDtoConverter).convertToDto(brand2);

        assertEquals(result, brandDtos);
    }

    @Test
    public void testGetById_whenBrandIdExists_shouldReturnBrandDto() {
        Brand brand = new Brand(1L, "brand", List.of());
        BrandDto brandDto = new BrandDto(1L, "brand");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandDtoConverter.convertToDto(brand)).thenReturn(brandDto);

        BrandDto result = brandService.getById(1L);

        verify(brandRepository).findById(1L);
        verify(brandDtoConverter).convertToDto(brand);

        assertEquals(result, brandDto);

    }

    @Test
    public void testGetById_whenBrandIdDoesNotExist_shouldThrowBrandNotFoundException() {

        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.getById(1L));

        verifyNoInteractions(brandDtoConverter);

    }

    @Test
    public void testCreate_whenCreateBrandCalledWithRequest_shouldReturnBrandDto() {
        CreateBrandRequest createBrandRequest = new CreateBrandRequest();
        createBrandRequest.setName("brand");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("brand");

        Brand savedBrand = new Brand();
        savedBrand.setId(1L);
        savedBrand.setName("brand");

        BrandDto brandDto = new BrandDto();
        brandDto.setId(1L);
        brandDto.setName("brand");

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(createBrandRequest, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(savedBrand);
        when(brandDtoConverter.convertToDto(savedBrand)).thenReturn(brandDto);

        BrandDto result = brandService.create(createBrandRequest);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(createBrandRequest, Brand.class);
        verify(brandRepository).save(brand);
        verify(brandDtoConverter).convertToDto(savedBrand);

        assertEquals(result, brandDto);
    }

    @Test
    public void testCreate_whenBrandNameExists_shouldThrowBrandNameExistsException() {
        CreateBrandRequest createBrandRequest = new CreateBrandRequest();
        createBrandRequest.setName("brand");

        doThrow(BrandNameExistsException.class).when(brandBusinessRules).checkIfBrandNameExists("brand");

        assertThrows(BrandNameExistsException.class, () -> brandService.create(createBrandRequest));

        verify(brandBusinessRules).checkIfBrandNameExists("brand");
        verifyNoInteractions(modelMapperService, brandRepository, brandDtoConverter);
    }

    @Test
    public void testUpdate_whenUpdateBrandCalledWithRequest_shouldReturnBrandDto() {
        UpdateBrandRequest updateBrandRequest = new UpdateBrandRequest();
        updateBrandRequest.setId(1L);
        updateBrandRequest.setName("brand");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("brand");

        BrandDto brandDto = new BrandDto();
        brandDto.setId(1L);
        brandDto.setName("brand");

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(updateBrandRequest, Brand.class)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(brand);
        when(brandDtoConverter.convertToDto(brand)).thenReturn(brandDto);

        BrandDto result = brandService.update(updateBrandRequest);

        verify(modelMapperService).forRequest();
        verify(modelMapper).map(updateBrandRequest, Brand.class);
        verify(brandRepository).save(brand);
        verify(brandDtoConverter).convertToDto(brand);

        assertEquals(result, brandDto);
    }

    @Test
    public void testUpdate_whenBrandIdDoesNotExists_shouldThrowBrandNotFoundException() {
        UpdateBrandRequest updateBrandRequest = new UpdateBrandRequest();
        updateBrandRequest.setId(1L);
        updateBrandRequest.setName("brand");

        doThrow(BrandNotFoundException.class).when(brandBusinessRules).checkIfBrandIdNotExists(1L);

        assertThrows(BrandNotFoundException.class, () -> brandService.update(updateBrandRequest));

        verify(brandBusinessRules).checkIfBrandIdNotExists(1L);
        verifyNoInteractions(modelMapperService, brandRepository, brandDtoConverter);
    }

    @Test
    public void testUpdate_whenBrandNameExists_shouldThrowBrandNameExistsException() {
        UpdateBrandRequest updateBrandRequest = new UpdateBrandRequest();
        updateBrandRequest.setId(1L);
        updateBrandRequest.setName("brand");

        doThrow(BrandNameExistsException.class).when(brandBusinessRules).checkIfBrandNameExists("brand");

        assertThrows(BrandNameExistsException.class, () -> brandService.update(updateBrandRequest));

        verify(brandBusinessRules).checkIfBrandNameExists("brand");
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











