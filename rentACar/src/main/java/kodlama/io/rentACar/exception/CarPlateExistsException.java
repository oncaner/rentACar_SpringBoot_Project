package kodlama.io.rentACar.exception;

public class CarPlateExistsException extends RuntimeException {
    public CarPlateExistsException(String message) {
        super(message);
    }
}
