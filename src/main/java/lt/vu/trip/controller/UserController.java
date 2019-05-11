package lt.vu.trip.controller;

import lt.vu.trip.entity.response.UserResponse;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<UserResponse> getCurrentUser() {
		User user = userService.getCurrentUser();
		return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getRoles()));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@PostMapping("")
	public ResponseEntity<UserResponse> create(@RequestBody User user) {
		User createdUser = userService.create(user);
		return ResponseEntity.ok(new UserResponse(createdUser.getId(), createdUser.getName(),
			createdUser.getSurname(), createdUser.getRoles()));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@PutMapping("")
	public ResponseEntity<UserResponse> updateUserRoles(@RequestBody User user) {
		User createdUser = userService.updateUserRoles(user);
		return ResponseEntity.ok(new UserResponse(createdUser.getId(), createdUser.getName(),
			createdUser.getSurname(), createdUser.getRoles()));
	}
}
