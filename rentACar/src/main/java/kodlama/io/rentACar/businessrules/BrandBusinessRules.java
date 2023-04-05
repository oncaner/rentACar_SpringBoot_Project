package kodlama.io.rentACar.businessrules;

import kodlama.io.rentACar.exception.BrandNameExistsException;
import kodlama.io.rentACar.exception.BrandNotFoundException;
import kodlama.io.rentACar.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BrandBusinessRules {

    private BrandRepository brandRepository;

    public void checkIfBrandNameExists(String name) {
        if (this.brandRepository.existsByName(name)) {
            throw new BrandNameExistsException(String.format("Brand name already exists with: %s", name));
        }
    }

    public void checkIfBrandIdNotExists(int id) {
        if (!this.brandRepository.existsById(id)) {
            throw new BrandNotFoundException(String.format("Brand not found with: %d", id));
        }
    }

}
