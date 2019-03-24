package lt.vu.trip.service.auth.jwt;

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
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserRepository users;

	@Override
	public String login(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		String token = this.jwtTokenProvider.createToken(username,
				this.users.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
						.getRoles()
		);
		return token;
	}
}
