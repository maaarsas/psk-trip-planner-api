package lt.vu.trip.controller;

import lt.vu.trip.entity.User;
import lt.vu.trip.entity.request.AuthenticationRequest;
import lt.vu.trip.entity.response.LoginResponse;
import lt.vu.trip.repository.UserRepository;
import lt.vu.trip.service.auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * THIS CLASS IS DEMO. THIS CODE IS TEMPORARY AND IT WILL BE REMOVED
 */

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository users;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("")
	public String all() {
		return "{\"success\":1}";
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody AuthenticationRequest data) {
		String email = data.getEmail();
		String password = data.getPassword();
		User newUser = User.builder()
				.username(email)
				.password(passwordEncoder.encode(password))
				.roles(Arrays.asList("ROLE_USER"))
				.build();
		users.save(newUser);

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
		String token = jwtTokenProvider.createToken(email,
				users.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("Email " + email + "not found")).getRoles());
		return ResponseEntity.ok(new LoginResponse(email, token));
	}
}

