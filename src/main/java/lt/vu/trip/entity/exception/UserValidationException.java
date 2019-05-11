package lt.vu.trip.entity.exception;

public class UserValidationException extends RuntimeException {

	public UserValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserValidationException(String message) {
		super(message);
	}

}
