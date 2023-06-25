package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.requests.CreateModelRequest;
import kodlama.io.rentACar.dto.requests.UpdateModelRequest;
import kodlama.io.rentACar.dto.responses.GetAllModelsResponse;
import kodlama.io.rentACar.dto.responses.GetByIdModelResponse;
import kodlama.io.rentACar.model.Model;

import java.util.List;

public interface ModelService {
    List<GetAllModelsResponse> getAll();

    List<GetAllModelsResponse> getAllByOrderByNameAsc();

    List<GetAllModelsResponse> getAllByOrderByNameDesc();

    GetByIdModelResponse getById(Long id);

    Model create(CreateModelRequest createModelRequest);

    Model update(UpdateModelRequest updateModelRequest);

    void delete(Long id);


}
