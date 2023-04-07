package kodlama.io.rentACar.controller;

import jakarta.validation.Valid;
import kodlama.io.rentACar.dto.converter.ModelDto;
import kodlama.io.rentACar.dto.converter.ModelDtoConverter;
import kodlama.io.rentACar.service.ModelService;
import kodlama.io.rentACar.dto.requests.CreateModelRequest;
import kodlama.io.rentACar.dto.requests.UpdateModelRequest;
import kodlama.io.rentACar.dto.responses.GetAllModelsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdModelResponse;
import kodlama.io.rentACar.model.Model;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@AllArgsConstructor
public class ModelController {

    private final ModelService modelService;
    private final ModelDtoConverter modelDtoConverter;

    @GetMapping()
    public ResponseEntity<List<GetAllModelsResponse>> getAll() {
        List<GetAllModelsResponse> responseList = this.modelService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-name-asc")
    public ResponseEntity<List<GetAllModelsResponse>> getAllByOrderByNameAsc() {
        List<GetAllModelsResponse> responseList = this.modelService.getAllByOrderByNameAsc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/order-by-name-desc")
    public ResponseEntity<List<GetAllModelsResponse>> getAllByOrderByNameDesc() {
        List<GetAllModelsResponse> responseList = this.modelService.getAllByOrderByNameDesc();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdModelResponse> getById(@PathVariable int id) {
        GetByIdModelResponse response = this.modelService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ModelDto> create(@RequestBody @Valid CreateModelRequest createModelRequest) {
        Model model = this.modelService.create(createModelRequest);

        return new ResponseEntity<>(modelDtoConverter.convertToDto(model), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ModelDto> update(@RequestBody @Valid UpdateModelRequest updateModelRequest) {
        Model model = this.modelService.update(updateModelRequest);

        return new ResponseEntity<>(modelDtoConverter.convertToDto(model), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.modelService.delete(id);
    }

}
