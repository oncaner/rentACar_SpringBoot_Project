package kodlama.io.rentACar.dto.converter;

import kodlama.io.rentACar.dto.response.ModelDto;
import kodlama.io.rentACar.model.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelDtoConverter {

    public ModelDto convertToDto(Model model) {
        return new ModelDto(
                model.getId(),
                model.getName(),
                model.getBrand().getName()
        );
    }

}
