package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.businessrules.CustomerBusinessRules;
import kodlama.io.rentACar.configuration.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.RentalDto;
import kodlama.io.rentACar.dto.RentalDtoConverter;
import kodlama.io.rentACar.dto.request.CreateRentalRequest;
import kodlama.io.rentACar.exception.CarCannotBeRentedException;
import kodlama.io.rentACar.exception.InvalidRentDateException;
import kodlama.io.rentACar.exception.RentalNotFoundException;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Rental;
import kodlama.io.rentACar.repository.RentalRepository;
import kodlama.io.rentACar.service.CarService;
import kodlama.io.rentACar.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final ModelMapperService modelMapperService;
    private final CustomerBusinessRules customerBusinessRules;
    private final RentalDtoConverter rentalDtoConverter;

    @Override
    public List<RentalDto> getAll() {
        List<Rental> rentals = this.rentalRepository.findAll();

        return rentals.stream()
                .map(rental -> this.modelMapperService.forResponse()
                        .map(rental, RentalDto.class)).toList();
    }

    @Override
    public RentalDto create(CreateRentalRequest createRentalRequest) {
        this.customerBusinessRules.checkIfCustomerIdNotExists(createRentalRequest.getCustomerId());

        if (createRentalRequest.getStartDate().isAfter(createRentalRequest.getEndDate())) {
            throw new InvalidRentDateException("Geçersiz kiralama tarihleri. Başlangıç tarihi, bitiş tarihinden önce olmalıdır.");
        }

        Car car = this.modelMapperService.forResponse()
                .map(this.carService.getById(createRentalRequest.getCarId()), Car.class);

        if (car.getState() != 1) {
            throw new CarCannotBeRentedException("Araç kiralanamaz durumda.");
        }

        Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);

        return this.rentalDtoConverter.convertToDto(this.rentalRepository.save(rental));
    }

    @Override
    public void delete(Long id) {

        if (!this.rentalRepository.existsById(id)) {
            throw new RentalNotFoundException(String.format("Rental not found with: %d", id));
        }

        this.rentalRepository.deleteById(id);

    }
}
