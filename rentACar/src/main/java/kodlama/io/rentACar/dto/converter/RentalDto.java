package kodlama.io.rentACar.dto.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private Long id;

    private Long customerId;

    private String firstName;

    private String lastName;

    private Long carId;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private Long modelId;

    private String modelName;

    private Long brandId;

    private String brandName;

    private LocalDate startDate;

    private LocalDate endDate;

}
