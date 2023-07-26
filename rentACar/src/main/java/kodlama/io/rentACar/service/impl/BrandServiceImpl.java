package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.BrandDtoConverter;
import kodlama.io.rentACar.dto.request.CreateBrandRequest;
import kodlama.io.rentACar.dto.request.UpdateBrandRequest;
import kodlama.io.rentACar.dto.response.BrandDto;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.repository.BrandRepository;
import kodlama.io.rentACar.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapperService modelMapperService;
    private final BrandBusinessRules brandBusinessRules;
    private final BrandDtoConverter brandDtoConverter;

    @Override
    public List<BrandDto> getAll() {
        List<Brand> brands = this.brandRepository.findAll();

        return brands.stream().map(this.brandDtoConverter::convertToDto).toList();
    }

    @Override
    public List<BrandDto> getAllByOrderByNameDesc() {
        List<Brand> brands = this.brandRepository.findAllByOrderByNameDesc();

        return brands.stream().map(this.brandDtoConverter::convertToDto).toList();
    }

    @Override
    public List<BrandDto> getAllByOrderByNameAsc() {
        List<Brand> brands = this.brandRepository.findAllByOrderByNameAsc();

        return brands.stream().map(this.brandDtoConverter::convertToDto).toList();
    }

    @Override
    public BrandDto getById(Long id) {
        Brand brand = this.brandRepository.findById(id).orElseThrow(() ->new BrandNotFoundException(String.format("Brand not found with: %d", id)));

        return this.brandDtoConverter.convertToDto(brand);
    }

    @Override
    public BrandDto create(CreateBrandRequest createBrandRequest) {
        this.brandBusinessRules.checkIfBrandNameExists(createBrandRequest.getName());

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        return this.brandDtoConverter.convertToDto(this.brandRepository.save(brand));
    }

    @Override
    public BrandDto update(UpdateBrandRequest updateBrandRequest) {
        this.brandBusinessRules.checkIfBrandIdNotExists(updateBrandRequest.getId());
        this.brandBusinessRules.checkIfBrandNameExists(updateBrandRequest.getName());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        return this.brandDtoConverter.convertToDto(this.brandRepository.save(brand));
    }

    @Override
    public void delete(Long id) {
        this.brandBusinessRules.checkIfBrandIdNotExists(id);

        this.brandRepository.deleteById(id);
    }

}
