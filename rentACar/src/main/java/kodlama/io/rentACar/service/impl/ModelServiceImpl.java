package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.BrandBusinessRules;
import kodlama.io.rentACar.businessrules.ModelBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.response.ModelDto;
import kodlama.io.rentACar.dto.converter.ModelDtoConverter;
import kodlama.io.rentACar.dto.request.CreateModelRequest;
import kodlama.io.rentACar.dto.request.UpdateModelRequest;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.model.Brand;
import kodlama.io.rentACar.model.Model;
import kodlama.io.rentACar.repository.ModelRepository;
import kodlama.io.rentACar.service.BrandService;
import kodlama.io.rentACar.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandService brandService;
    private final ModelMapperService modelMapperService;
    private final ModelBusinessRules modelBusinessRules;
    private final BrandBusinessRules brandBusinessRules;
    private final ModelDtoConverter modelDtoConverter;

    @Override
    public List<ModelDto> getAll() {
        List<Model> models = this.modelRepository.findAll();

        return models.stream().map(this.modelDtoConverter::convertToDto).toList();
    }

    @Override
    public List<ModelDto> getAllByOrderByNameAsc() {
        List<Model> models = this.modelRepository.findAllByOrderByNameAsc();

        return models.stream().map(this.modelDtoConverter::convertToDto).toList();
    }

    @Override
    public List<ModelDto> getAllByOrderByNameDesc() {
        List<Model> models = this.modelRepository.findAllByOrderByNameDesc();

        return models.stream().map(this.modelDtoConverter::convertToDto).toList();
    }

    @Override
    public ModelDto getById(Long id) {
        Model model = this.modelRepository.findById(id).orElseThrow(
                () -> new ModelNotFoundException(String.format("Model not found with: %d", id)));

        return this.modelDtoConverter.convertToDto(model);
    }

    @Override
    public ModelDto create(CreateModelRequest createModelRequest) {
        this.modelBusinessRules.checkIfModelNameExists(createModelRequest.getName());
        this.brandBusinessRules.checkIfBrandIdNotExists(createModelRequest.getBrandId());

        Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);

        return this.modelDtoConverter.convertToDto(this.modelRepository.save(model));
    }

    @Override
    public ModelDto update(UpdateModelRequest updateModelRequest) {
        this.modelBusinessRules.checkIfModelIdNotExists(updateModelRequest.getId());
        this.modelBusinessRules.checkIfModelNameExists(updateModelRequest.getName());

        Model oldModel = this.modelRepository.findById(updateModelRequest.getId()).orElseThrow();
        Brand brand = this.modelMapperService.forResponse()
                .map(this.brandService.getById(oldModel.getBrand().getId()),Brand.class);

        Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);
        model.setBrand(brand);

        return this.modelDtoConverter.convertToDto(this.modelRepository.save(model));
    }

    @Override
    public void delete(Long id) {
        this.modelBusinessRules.checkIfModelIdNotExists(id);

        this.modelRepository.deleteById(id);
    }

}
