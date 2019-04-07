package lt.vu.trip.service.auth.jwt;

import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.repository.UserRepository;
import lt.vu.trip.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthService implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository users;

	@Override
	public String login(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		String token = jwtTokenProvider.createToken(email,
				users.findByUsername(email)
						.orElseThrow(() -> new UsernameNotFoundException(ErrorType.AUTHENTICATION_EMAIL_NOT_FOUND.toString()))
						.getRoles()
		);
		return token;
	}
}
