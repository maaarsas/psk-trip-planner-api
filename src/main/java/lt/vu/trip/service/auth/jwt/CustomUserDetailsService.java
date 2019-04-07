package lt.vu.trip.service.auth.jwt;

import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository users;

	public CustomUserDetailsService(UserRepository users) {
		this.users = users;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return users.findByUsername(email)
				.orElseThrow(() -> new UsernameNotFoundException(ErrorType.AUTHENTICATION_USERNAME_NOT_FOUND.toString()));
	}
}
