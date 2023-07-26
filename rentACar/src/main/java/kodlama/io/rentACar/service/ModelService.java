package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.response.ModelDto;
import kodlama.io.rentACar.dto.request.CreateModelRequest;
import kodlama.io.rentACar.dto.request.UpdateModelRequest;

import java.util.List;

public interface ModelService {
    List<ModelDto> getAll();

    List<ModelDto> getAllByOrderByNameAsc();

    List<ModelDto> getAllByOrderByNameDesc();

    ModelDto getById(Long id);

    ModelDto create(CreateModelRequest createModelRequest);

    ModelDto update(UpdateModelRequest updateModelRequest);

    void delete(Long id);


}
