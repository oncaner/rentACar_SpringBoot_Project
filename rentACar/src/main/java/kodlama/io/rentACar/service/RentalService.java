package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.RentalDto;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;

import java.util.List;

public interface RentalService {

    List<RentalDto> getAll();

    RentalDto create(CreateRentalRequest createRentalRequest);

    void delete(Long id);

}
