package kodlama.io.rentACar.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsResponse {

    private int id;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private int state;

    private String brandName;

    private int brandId;

    private String modelName;

    private int modelId;
}
