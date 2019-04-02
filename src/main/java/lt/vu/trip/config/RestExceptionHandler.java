package lt.vu.trip.config;

import lombok.extern.slf4j.Slf4j;
import lt.vu.trip.entity.exception.TripValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity constraintViolation(ConstraintViolationException ex, WebRequest request) {
		log.debug("handling ConstraintViolationException...");
		return new ResponseEntity<>(
				"Bad input data, more info: " + ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({TripValidationException.class})
	public ResponseEntity tripValidation(TripValidationException ex, WebRequest request) {
		log.debug("handling TripValidationException...");
		return new ResponseEntity<>(
				"Bad trip input data: " + ex.getMessage(), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}
}
