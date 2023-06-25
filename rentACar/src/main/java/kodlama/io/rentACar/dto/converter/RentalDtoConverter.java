package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.model.*;
import kodlama.io.rentACar.repository.CarRepository;
import kodlama.io.rentACar.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RentalDtoConverter {

    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public RentalDto convertToDto(Rental rental) {

        Customer customer = customerRepository.findById(rental.getCustomer().getId()).orElseThrow();
        Car car = carRepository.findById(rental.getCar().getId()).orElseThrow();

        return new RentalDto(
                rental.getId(),
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                car.getId(),
                car.getPlate(),
                car.getDailyPrice(),
                car.getModelYear(),
                car.getModel().getId(),
                car.getModel().getName(),
                car.getModel().getBrand().getId(),
                car.getModel().getBrand().getName(),
                rental.getStartDate(),
                rental.getEndDate()
        );
    }

}
