package lt.vu.trip.config;

import lombok.extern.slf4j.Slf4j;
import lt.vu.trip.entity.exception.OfficeValidationException;
import lt.vu.trip.entity.exception.TripValidationException;
import lt.vu.trip.entity.exception.UserValidationException;
import lt.vu.trip.entity.response.ErrorResponse;
import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.service.trip.TripMergeException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({TripValidationException.class})
	public ResponseEntity tripValidation(TripValidationException ex, WebRequest request) {
		log.debug("handling TripValidationException...");
		return new ResponseEntity<>(
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler({OfficeValidationException.class})
	public ResponseEntity officeValidation(OfficeValidationException ex, WebRequest request) {
		log.debug("handling OfficeValidationException...");
		return new ResponseEntity<>(
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler({TripMergeException.class})
	public ResponseEntity tripMerge(TripMergeException ex, WebRequest request) {
		log.debug("handling TripMergeException...");
		return new ResponseEntity<>(
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler({UserValidationException.class})
	public ResponseEntity userValidation(UserValidationException ex, WebRequest request) {
		log.debug("handling UserValidationException...");
		return new ResponseEntity<>(
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity notFound(ResourceNotFoundException ex, WebRequest request) {
		log.debug("handling ResourceNotFoundException...");
		return new ResponseEntity<>(
			createError(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	private ErrorResponse createError(String errorMessage) {
		try {
			return new ErrorResponse(ErrorType.valueOf(errorMessage));
		} catch (Exception e) {
			return new ErrorResponse(ErrorType.BE_FAILURE);
		}
	}
}
