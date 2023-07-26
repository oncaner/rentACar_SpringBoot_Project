package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.response.ModelDto;
import kodlama.io.rentACar.dto.request.CreateModelRequest;
import kodlama.io.rentACar.dto.request.UpdateModelRequest;
import kodlama.io.rentACar.service.ModelService;
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
@RequestMapping("/api/models")
@AllArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping()
    public ResponseEntity<List<ModelDto>> getAll() {

        return new ResponseEntity<>(this.modelService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/order-by-name-asc")
    public ResponseEntity<List<ModelDto>> getAllByOrderByNameAsc() {

        return new ResponseEntity<>(this.modelService.getAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/order-by-name-desc")
    public ResponseEntity<List<ModelDto>> getAllByOrderByNameDesc() {

        return new ResponseEntity<>(this.modelService.getAllByOrderByNameDesc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDto> getById(@PathVariable Long id) {

        return new ResponseEntity<>(this.modelService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ModelDto> create(@RequestBody @Valid CreateModelRequest createModelRequest) {

        return new ResponseEntity<>(this.modelService.create(createModelRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ModelDto> update(@RequestBody @Valid UpdateModelRequest updateModelRequest) {

        return new ResponseEntity<>(this.modelService.update(updateModelRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.modelService.delete(id);

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
