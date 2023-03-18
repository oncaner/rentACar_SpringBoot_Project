package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.core.utilities.exceptions.BusinessException;
import kodlama.io.rentACar.core.utilities.exceptions.CarNotFoundException;
import kodlama.io.rentACar.dataAccess.abstracts.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {

    private CarRepository carRepository;

    public void checkIfPlateExists(String name) {
        if (carRepository.existsByPlate(name)) {
            throw new BusinessException("Plate already exists");
        }
    }

    public void checkIfCarIdNotExists(int id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(String.format("Car not found with: %d", id));
        }
    }

}
