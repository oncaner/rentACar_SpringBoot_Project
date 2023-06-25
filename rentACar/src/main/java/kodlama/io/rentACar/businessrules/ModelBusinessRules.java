package kodlama.io.rentACar.businessrules;

import kodlama.io.rentACar.exception.ModelNameExistsException;
import kodlama.io.rentACar.exception.ModelNotFoundException;
import kodlama.io.rentACar.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class ModelBusinessRules {

    private ModelRepository modelRepository;

    public void checkIfModelNameExists(String name) {
        if (this.modelRepository.existsByName(name)) {
            throw new ModelNameExistsException(String.format("Model name already exists with: %s", name));
        }
    }

    public void checkIfModelIdNotExists(Long id) {
        if (!this.modelRepository.existsById(id)) {
            throw new ModelNotFoundException(String.format("Model not found with: %d", id));
        }
    }

}
