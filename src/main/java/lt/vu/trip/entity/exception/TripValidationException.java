package lt.vu.trip.entity.exception;

public class TripValidationException extends RuntimeException {

	public TripValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TripValidationException(String message) {
		super(message);
	}
}
