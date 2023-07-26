package kodlama.io.rentACar.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRentalRequest {

    @NotNull
    private Long carId;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
