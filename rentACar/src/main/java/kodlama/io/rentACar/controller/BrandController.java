package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.response.BrandDto;
import kodlama.io.rentACar.dto.request.CreateBrandRequest;
import kodlama.io.rentACar.dto.request.UpdateBrandRequest;
import kodlama.io.rentACar.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping()
    public ResponseEntity<List<BrandDto>> getAll() {

        return new ResponseEntity<>(this.brandService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/order-by-name-desc")
    public ResponseEntity<List<BrandDto>> getAllByOrderByNameDesc() {

        return new ResponseEntity<>(this.brandService.getAllByOrderByNameDesc(), HttpStatus.OK);
    }

    @GetMapping("/order-by-name-asc")
    public ResponseEntity<List<BrandDto>> getAllByOrderByNameAsc() {

        return new ResponseEntity<>(this.brandService.getAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getById(@PathVariable Long id) {

        return new ResponseEntity<>(this.brandService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BrandDto> create(@RequestBody @Valid CreateBrandRequest createBrandRequest) {

        return new ResponseEntity<>(this.brandService.create(createBrandRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BrandDto> update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {

        return new ResponseEntity<>(this.brandService.update(updateBrandRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        this.brandService.delete(id);

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
