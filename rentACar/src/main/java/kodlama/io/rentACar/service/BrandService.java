package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.requests.CreateBrandRequest;
import kodlama.io.rentACar.dto.requests.UpdateBrandRequest;
import kodlama.io.rentACar.dto.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.model.Brand;

import java.util.List;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();

    GetByIdBrandResponse getById(int id);

    Brand create(CreateBrandRequest createBrandRequest);

    Brand update(UpdateBrandRequest updateBrandRequest);

    void delete(int id);


}
