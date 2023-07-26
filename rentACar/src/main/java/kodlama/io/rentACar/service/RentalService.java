package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.response.RentalDto;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;

import java.util.List;

public interface RentalService {

    List<RentalDto> getAll();

    RentalDto create(CreateRentalRequest createRentalRequest);

    void delete(Long id);

}
