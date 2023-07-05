package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.BrandDto;
import kodlama.io.rentACar.dto.request.CreateBrandRequest;
import kodlama.io.rentACar.dto.request.UpdateBrandRequest;

import java.util.List;

public interface BrandService {
    List<BrandDto> getAll();

    List<BrandDto> getAllByOrderByNameDesc();

    List<BrandDto> getAllByOrderByNameAsc();

    BrandDto getById(Long id);

    BrandDto create(CreateBrandRequest createBrandRequest);

    BrandDto update(UpdateBrandRequest updateBrandRequest);

    void delete(Long id);


}
