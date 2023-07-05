package kodlama.io.rentACar.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarByModelYearRequest {

    @NotNull
    private Long id;

    @NotNull
    private int modelYear;
}
