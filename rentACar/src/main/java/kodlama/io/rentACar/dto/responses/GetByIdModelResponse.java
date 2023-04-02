package kodlama.io.rentACar.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdModelResponse {

    private int id;

    private String name;

    private String brandName;
}
