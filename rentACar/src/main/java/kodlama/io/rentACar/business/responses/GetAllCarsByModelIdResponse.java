package kodlama.io.rentACar.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCarsByModelIdResponse {
    private int id;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private int state;

    private String brandName;

    private String modelName;

}
