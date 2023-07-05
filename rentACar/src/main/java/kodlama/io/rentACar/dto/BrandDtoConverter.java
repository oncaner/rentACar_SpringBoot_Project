package kodlama.io.rentACar.dto;

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
