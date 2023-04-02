package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarDtoConverter {

    public CarDto convertToDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getPlate(),
                car.getDailyPrice(),
                car.getModelYear(),
                car.getState(),
                car.getModel().getName(),
                car.getModel().getBrand().getName()
        );
    }
}
