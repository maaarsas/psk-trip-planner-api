package lt.vu.trip.service.user;

import lt.vu.trip.entity.exception.UserValidationException;
import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

	public void validate(User user) {
		if (user.getUsername() == null || user.getUsername().trim().equals("")) {
			throw new UserValidationException(ErrorType.USER_EMAIL.toString());
		}
		if (user.getPassword() == null || user.getPassword().trim().equals("")) {
			throw new UserValidationException(ErrorType.USER_PASSWORD.toString());
		}
		if (user.getName() == null || user.getName().trim().equals("")) {
			throw new UserValidationException(ErrorType.USER_NAME.toString());
		}
		if (user.getSurname() == null || user.getSurname().trim().equals("")) {
			throw new UserValidationException(ErrorType.USER_SURNAME.toString());
		}
	}
}
