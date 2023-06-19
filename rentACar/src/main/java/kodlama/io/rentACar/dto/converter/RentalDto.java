package kodlama.io.rentACar.dto.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private int id;

    private int customerId;

    private String firstName;

    private String lastName;

    private int carId;

    private String plate;

    private double dailyPrice;

    private int modelYear;

    private LocalDate startDate;

    private LocalDate endDate;

}
