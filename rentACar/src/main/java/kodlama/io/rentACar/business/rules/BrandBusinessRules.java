package kodlama.io.rentACar.business.rules;

import kodlama.io.rentACar.core.utilities.exceptions.BrandNotFoundException;
import kodlama.io.rentACar.core.utilities.exceptions.BusinessException;
import kodlama.io.rentACar.dataAccess.abstracts.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BrandBusinessRules {

    private BrandRepository brandRepository;

    public void checkIfBrandNameExists(String name) {
        if (this.brandRepository.existsByName(name)) {
            throw new BusinessException("Brand name already exists");
        }
    }

    public void checkIfBrandIdNotExists(int id) {
        if (!this.brandRepository.existsById(id)) {
            throw new BrandNotFoundException(String.format("Brand not found with: %d", id));
        }
    }

}
