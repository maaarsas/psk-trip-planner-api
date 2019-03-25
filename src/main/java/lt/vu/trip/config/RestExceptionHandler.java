package lt.vu.trip.config;

import lombok.extern.slf4j.Slf4j;
import lt.vu.trip.service.auth.jwt.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler({InvalidJwtAuthenticationException.class})
	public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
		log.debug("handling InvalidJwtAuthenticationException...");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
