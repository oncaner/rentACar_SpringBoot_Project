package kodlama.io.rentACar.businessrules;

import kodlama.io.rentACar.exception.CarNotFoundException;
import kodlama.io.rentACar.exception.CarPlateExistsException;
import kodlama.io.rentACar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {

    private CarRepository carRepository;

    public void checkIfPlateExists(String plate) {
        if (carRepository.existsByPlate(plate)) {
            throw new CarPlateExistsException(String.format("Plate already exists with: %s", plate));
        }
    }

    public void checkIfCarIdNotExists(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(String.format("Car not found with: %d", id));
        }
    }

}
