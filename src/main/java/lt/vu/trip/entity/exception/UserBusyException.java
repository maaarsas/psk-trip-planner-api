package lt.vu.trip.entity.exception;

import lt.vu.trip.entity.user.User;

public class UserBusyException extends RuntimeException {

	private final User user;

	public UserBusyException(String message, User user) {
		super(message);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
