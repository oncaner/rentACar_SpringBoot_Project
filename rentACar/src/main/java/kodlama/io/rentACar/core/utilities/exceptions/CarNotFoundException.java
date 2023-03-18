package kodlama.io.rentACar.core.utilities.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message) {
        super(message);
    }
}
