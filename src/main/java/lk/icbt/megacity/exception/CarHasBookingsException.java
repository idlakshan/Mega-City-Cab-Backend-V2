package lk.icbt.megacity.exception;

public class CarHasBookingsException extends RuntimeException {

    public CarHasBookingsException(String message) {
        super(message);
    }

    public CarHasBookingsException(String message, Throwable cause) {
        super(message, cause);
    }
}