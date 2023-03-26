package kodlama.io.rentACar.business.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarWithDailyPrice {
    @NotNull
    private int id;

    @NotNull
    private double dailyPrice;
}
