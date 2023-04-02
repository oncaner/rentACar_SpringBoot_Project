package kodlama.io.rentACar.dto.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private int id;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private int state;

    private String modelName;

    private String brandName;

}
