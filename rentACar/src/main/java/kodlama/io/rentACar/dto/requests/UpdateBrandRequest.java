package kodlama.io.rentACar.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandRequest {

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
}
