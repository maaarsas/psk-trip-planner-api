package lt.vu.trip.entity.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
	USER("ROLE_USER"),
	ORGANIZER("ROLE_ORGANIZER"),
	ADMINISTRATOR("ROLE_ADMINISTRATOR");

	private String value;

	Role(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static List<String> getValues() {
		return Arrays.stream(Role.values()).map(Role::value)
				.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return value;
	}
}
