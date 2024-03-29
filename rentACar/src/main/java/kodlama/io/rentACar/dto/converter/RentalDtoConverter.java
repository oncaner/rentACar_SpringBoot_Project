package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.dto.response.RentalDto;
import kodlama.io.rentACar.model.Car;
import kodlama.io.rentACar.model.Customer;
import kodlama.io.rentACar.model.Rental;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RentalDtoConverter {

    public RentalDto convertToDto(Rental rental) {

        Customer customer = rental.getCustomer();
        Car car = rental.getCar();

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
