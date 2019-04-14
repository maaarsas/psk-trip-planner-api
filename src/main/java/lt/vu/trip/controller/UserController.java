package lt.vu.trip.controller;

import lt.vu.trip.entity.user.User;
import lt.vu.trip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUsers(@NotNull String query) {
		List<User> users = userService.searchUsers(query);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/me")
	public ResponseEntity<User> getCurrentUser() {
		User currentUser = userService.getCurrentUser();
		return ResponseEntity.ok(currentUser);
	}
}
