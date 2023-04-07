package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.converter.BrandDto;
import kodlama.io.rentACar.dto.converter.BrandDtoConverter;
import kodlama.io.rentACar.dto.requests.CreateBrandRequest;
import kodlama.io.rentACar.dto.requests.UpdateBrandRequest;
import kodlama.io.rentACar.dto.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;
    private final BrandDtoConverter brandDtoConverter;

    @GetMapping()
    public ResponseEntity<List<GetAllBrandsResponse>> getAll() {
        List<GetAllBrandsResponse> responseList = this.brandService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-name-desc")
    public ResponseEntity<List<GetAllBrandsResponse>> getAllByOrderByNameDesc() {
        List<GetAllBrandsResponse> responseList = this.brandService.getAllByOrderByNameDesc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-name-asc")
    public ResponseEntity<List<GetAllBrandsResponse>> getAllByOrderByNameAsc() {
        List<GetAllBrandsResponse> responseList = this.brandService.getAllByOrderByNameAsc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdBrandResponse> getById(@PathVariable int id) {
        GetByIdBrandResponse response = this.brandService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BrandDto> create(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
        Brand brand = this.brandService.create(createBrandRequest);

        return new ResponseEntity<>(brandDtoConverter.convertToDto(brand), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BrandDto> update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
        Brand brand = this.brandService.update(updateBrandRequest);

        return new ResponseEntity<>(brandDtoConverter.convertToDto(brand), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.brandService.delete(id);
    }

}
