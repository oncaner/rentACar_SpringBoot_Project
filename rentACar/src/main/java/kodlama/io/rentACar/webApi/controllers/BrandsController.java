package kodlama.io.rentACar.webApi.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.entities.concretes.Brand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandsController {
    private final BrandService brandService;

    @GetMapping()
    public ResponseEntity<List<GetAllBrandsResponse>> getAll() {
        List<GetAllBrandsResponse> responseList = this.brandService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdBrandResponse> getById(@PathVariable int id) {
        GetByIdBrandResponse response = this.brandService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Brand> create(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
        Brand brand = this.brandService.create(createBrandRequest);

        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Brand> update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
        Brand brand = this.brandService.update(updateBrandRequest);

        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.brandService.delete(id);
    }

}
