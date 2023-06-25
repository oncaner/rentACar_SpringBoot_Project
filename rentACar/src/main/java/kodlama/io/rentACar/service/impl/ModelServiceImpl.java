package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.config.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.requests.CreateModelRequest;
import kodlama.io.rentACar.dto.requests.UpdateModelRequest;
import kodlama.io.rentACar.dto.responses.GetAllModelsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdModelResponse;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.BrandRepository;
import kodlama.io.rentACar.repository.ModelRepository;
import kodlama.io.rentACar.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private BrandRepository brandRepository;
    private ModelMapperService modelMapperService;
    private ModelBusinessRules modelBusinessRules;
    private BrandBusinessRules brandBusinessRules;

    @Override
    public List<GetAllModelsResponse> getAll() {
        List<Model> models = this.modelRepository.findAll();

        return models.stream()
                .map(model -> this.modelMapperService.forResponse()
                        .map(model, GetAllModelsResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetAllModelsResponse> getAllByOrderByNameAsc() {
        List<Model> models = this.modelRepository.findAllByOrderByNameAsc();

        return models.stream()
                .map(model -> this.modelMapperService.forResponse()
                        .map(model, GetAllModelsResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetAllModelsResponse> getAllByOrderByNameDesc() {
        List<Model> models = this.modelRepository.findAllByOrderByNameDesc();

        return models.stream()
                .map(model -> this.modelMapperService.forResponse()
                        .map(model, GetAllModelsResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetByIdModelResponse getById(Long id) {
        Model model = this.modelRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(String.format("Model not found with: %d", id)));

        return this.modelMapperService.forResponse().map(model, GetByIdModelResponse.class);
    }

    @Override
    public Model create(CreateModelRequest createModelRequest) {
        this.modelBusinessRules.checkIfModelNameExists(createModelRequest.getName());
        this.brandBusinessRules.checkIfBrandIdNotExists(createModelRequest.getBrandId());

        Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);

        return this.modelRepository.save(model);
    }

    @Override
    public Model update(UpdateModelRequest updateModelRequest) {
        this.modelBusinessRules.checkIfModelIdNotExists(updateModelRequest.getId());
        this.modelBusinessRules.checkIfModelNameExists(updateModelRequest.getName());

        Model oldModel = this.modelRepository.findById(updateModelRequest.getId()).orElseThrow();
        Brand brand = this.brandRepository.findById(oldModel.getBrand().getId()).orElseThrow();

        Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);
        model.setBrand(brand);

        return this.modelRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        this.modelBusinessRules.checkIfModelIdNotExists(id);

        this.modelRepository.deleteById(id);
    }

}
