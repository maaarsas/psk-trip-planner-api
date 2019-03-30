package lt.vu.trip.controller;

import lt.vu.trip.entity.request.AuthenticationRequest;
import lt.vu.trip.entity.response.LoginResponse;
import lt.vu.trip.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		String token = authService.login(email, password);
		return ResponseEntity.ok(new LoginResponse(email, token));
	}
}
