package lt.vu.trip.controller;

import lt.vu.trip.entity.User;
import lt.vu.trip.entity.request.AuthenticationRequest;
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
import java.util.HashMap;
import java.util.Map;

/**
 * THIS CLASS IS DEMO. THIS CODE IS TEMPORARY AND IT WILL BE REMOVED
 */

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserRepository users;

	@Autowired
	PasswordEncoder passwordEncoder;


	@GetMapping("")
	public String all() {
		return "{\"success\":1}";
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody AuthenticationRequest data) {
		String username = data.getUsername();
		String password = data.getPassword();
		User newUser = User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.roles(Arrays.asList("ROLE_USER"))
				.build();
		users.save(newUser);

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
		String token = jwtTokenProvider.createToken(username,
				users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
		Map<Object, Object> model = new HashMap<>();
		model.put("username", username);
		model.put("token", token);
		return ResponseEntity.ok(model);
	}
}

