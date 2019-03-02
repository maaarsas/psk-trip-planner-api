package lt.vu.trip.service.auth;

import lombok.extern.slf4j.Slf4j;
import lt.vu.trip.entity.User;
import lt.vu.trip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class RegistrationService {

	@Autowired
	UserRepository users;

	@Autowired
	PasswordEncoder passwordEncoder;

	public User register(String username, String password) {
		User newUser = User.builder()
			.username(username)
			.password(this.passwordEncoder.encode(password))
			.roles(Arrays.asList("ROLE_USER"))
			.build();
		this.users.save(newUser);
		return newUser;
	}
}
