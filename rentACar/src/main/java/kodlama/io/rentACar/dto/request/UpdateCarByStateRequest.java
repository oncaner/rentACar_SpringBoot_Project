package kodlama.io.rentACar.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCarByStateRequest {

    @NotNull
    private Long id;

    @NotNull
    private int state;
}
