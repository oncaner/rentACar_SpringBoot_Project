package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.config.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.requests.CreateBrandRequest;
import kodlama.io.rentACar.dto.requests.UpdateBrandRequest;
import kodlama.io.rentACar.dto.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.repository.BrandRepository;
import kodlama.io.rentACar.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // It is a Service layer.
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapperService modelMapperService;
    private final BrandBusinessRules brandBusinessRules;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> this.modelMapperService.forResponse()
                        .map(brand, GetAllBrandsResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetAllBrandsResponse> getAllByOrderByNameDesc() {
        List<Brand> brands = this.brandRepository.findAllByOrderByNameDesc();

        return brands.stream()
                .map(brand -> this.modelMapperService.forResponse()
                        .map(brand, GetAllBrandsResponse.class)).toList();
    }

    @Override
    public List<GetAllBrandsResponse> getAllByOrderByNameAsc() {
        List<Brand> brands = this.brandRepository.findAllByOrderByNameAsc();

        return brands.stream()
                .map(brand -> this.modelMapperService.forResponse()
                        .map(brand, GetAllBrandsResponse.class)).toList();
    }

    @Override
    public GetByIdBrandResponse getById(Long id) {
        Brand brand = this.brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException(String.format("Brand not found with: %d", id)));

        return this.modelMapperService.forResponse().map(brand, GetByIdBrandResponse.class);
    }

    @Override
    public Brand create(CreateBrandRequest createBrandRequest) {
        this.brandBusinessRules.checkIfBrandNameExists(createBrandRequest.getName());

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        return this.brandRepository.save(brand);
    }

    @Override
    public Brand update(UpdateBrandRequest updateBrandRequest) {
        this.brandBusinessRules.checkIfBrandIdNotExists(updateBrandRequest.getId());
        this.brandBusinessRules.checkIfBrandNameExists(updateBrandRequest.getName());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        return this.brandRepository.save(brand);
    }

    @Override
    public void delete(Long id) {
        this.brandBusinessRules.checkIfBrandIdNotExists(id);

        this.brandRepository.deleteById(id);
    }

}
