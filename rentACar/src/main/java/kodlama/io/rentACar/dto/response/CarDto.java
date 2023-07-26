package kodlama.io.rentACar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {

    private Long id;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private int state;

    private String modelName;

    private String brandName;

}
