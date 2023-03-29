package kodlama.io.rentACar.webApi.controllers;

import jakarta.validation.Valid;
import kodlama.io.rentACar.business.abstracts.ModelService;
import kodlama.io.rentACar.business.requests.CreateModelRequest;
import kodlama.io.rentACar.business.requests.UpdateModelRequest;
import kodlama.io.rentACar.business.responses.GetAllModelsResponse;
import kodlama.io.rentACar.business.responses.GetByIdModelResponse;
import kodlama.io.rentACar.entities.concretes.Model;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@AllArgsConstructor
public class ModelsController {

    private ModelService modelService;

    @GetMapping()
    public ResponseEntity<List<GetAllModelsResponse>> getAll() {
        List<GetAllModelsResponse> responseList = this.modelService.getAll();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdModelResponse> getById(@PathVariable int id) {
        GetByIdModelResponse response = this.modelService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Model> create(@RequestBody @Valid CreateModelRequest createModelRequest) {
        Model model = this.modelService.create(createModelRequest);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Model> update(@RequestBody @Valid UpdateModelRequest updateModelRequest) {
        Model model = this.modelService.update(updateModelRequest);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.modelService.delete(id);
    }

}
