package kodlama.io.rentACar.exception;

public class CarCannotBeRentedException extends RuntimeException {

    public CarCannotBeRentedException(String message) {
        super(message);
    }
}
