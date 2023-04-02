package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandDtoConverter {

    public BrandDto convertToDto(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getName()
        );
    }

}
