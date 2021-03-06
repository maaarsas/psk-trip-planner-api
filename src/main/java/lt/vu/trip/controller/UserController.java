package lt.vu.trip.controller;

import lt.vu.trip.entity.request.UserRequest;
import lt.vu.trip.entity.response.UserResponse;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.interceptor.LogInterceptor;
import lt.vu.trip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

	@LogInterceptor
	@GetMapping("/me")
	public ResponseEntity<UserResponse> getCurrentUser() {
		User user = userService.getCurrentUser();
		return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getRoles()));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@GetMapping("")
	public ResponseEntity<List<UserResponse>> getAll() {
		List<User> users = userService.getAll();
		List<UserResponse> userResponses = users.stream()
				.map(user -> new UserResponse(user.getId(), user.getName(), user.getSurname(), user.getRoles()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(userResponses);
	}

	@Secured("ROLE_ADMINISTRATOR")
	@PostMapping("")
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
		User createdUser = userService.create(userRequest);
		return ResponseEntity.ok(new UserResponse(createdUser.getId(), createdUser.getName(),
				createdUser.getSurname(), createdUser.getRoles()));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@PutMapping("/{id}/roles")
	public ResponseEntity<UserResponse> updateUserRoles(@PathVariable Long id, @RequestBody UserRequest userRequest) {
		User createdUser = userService.updateUserRoles(id, userRequest);
		return ResponseEntity.ok(new UserResponse(createdUser.getId(), createdUser.getName(),
				createdUser.getSurname(), createdUser.getRoles()));
	}

	@Secured("ROLE_ADMINISTRATOR")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

}
