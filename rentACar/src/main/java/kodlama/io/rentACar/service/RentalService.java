package kodlama.io.rentACar.service;

import kodlama.io.rentACar.dto.converter.RentalDto;
import kodlama.io.rentACar.dto.requests.CreateRentalRequest;
import kodlama.io.rentACar.model.Rental;

import java.util.List;

public interface RentalService {

    List<RentalDto> getAll();

    Rental create(CreateRentalRequest createRentalRequest);

    void delete(int id);

}
