package kodlama.io.rentACar.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarByModelYearRequest {

    @NotNull
    private int id;

    @NotNull
    private int modelYear;
}
