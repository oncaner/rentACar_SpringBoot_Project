package kodlama.io.rentACar.service.impl;

import kodlama.io.rentACar.config.mapper.ModelMapperService;
import kodlama.io.rentACar.dto.converter.RentalDto;
import kodlama.io.rentACar.dto.requests.CreateRentalRequest;
import kodlama.io.rentACar.exception.CarCannotBeRentedException;
import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.exception.InvalidRentDateException;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Rental;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.repository.RentalRepository;
import kodlama.io.rentACar.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public List<RentalDto> getAll() {
        List<Rental> rentals = this.rentalRepository.findAll();
        List<RentalDto> rentalDtos = rentals.stream()
                .map(rental -> this.modelMapperService.forResponse()
                        .map(rental, RentalDto.class)).toList();

        return rentalDtos;
    }

    @Override
    public Rental create(CreateRentalRequest createRentalRequest) {

        if (createRentalRequest.getStartDate().isAfter(createRentalRequest.getEndDate())) {
            throw new InvalidRentDateException("Geçersiz kiralama tarihleri. Başlangıç tarihi, bitiş tarihinden önce olmalıdır.");
        }

        Car car = this.carRepository.findById(Math.toIntExact(createRentalRequest.getCarId()))
                .orElseThrow(() -> new CarNotFoundException("Araç bulunamadı"));

        if (car.getState() != 1) {
            throw new CarCannotBeRentedException("Araç kiralanamaz durumda.");
        }

        Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);

        return this.rentalRepository.save(rental);
    }

    @Override
    public void delete(int id) {

        this.rentalRepository.deleteById(id);

    }
}
