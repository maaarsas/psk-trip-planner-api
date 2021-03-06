package lt.vu.trip.service.user;

import lt.vu.trip.entity.request.UserRequest;
import lt.vu.trip.entity.user.Role;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserValidator validator;

	public User getCurrentUser() {
		Long currentUserId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		Optional<User> user = userRepository.findById(currentUserId);
		return user.orElse(null);
	}

	public List<User> searchUsers(String query) {
		if (query.isEmpty()) {
			return new ArrayList<>();
		}

		String[] queryParts = query.trim().split(" ");
		if (queryParts.length > 1) {
			return userRepository.findTop10ByNameStartingWithIgnoreCaseAndSurnameStartingWithIgnoreCase(queryParts[0], queryParts[1]);
		} else {
			return userRepository.findTop10ByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(query, query);
		}
	}

	public User getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElse(null);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User create(UserRequest userRequest) {
		User newUser = User.builder()
			.username(userRequest.getUsername())
			.password(passwordEncoder.encode(userRequest.getPassword()))
			.surname(userRequest.getSurname())
			.name(userRequest.getName())
			.roles((userRequest.getRoles() == null || userRequest.getRoles().isEmpty()) ? Arrays.asList(Role.USER) : userRequest.getRoles())
			.build();
		validator.validate(newUser);
		userRepository.save(newUser);

		return newUser;
	}

	public User updateUserRoles(Long id, UserRequest userRequest) {
		User existingUser = userRepository.findById(id).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException();
		}

		List<Role> roles = new ArrayList<>();

		switch (userRequest.getRoles().get(0)) {
			case USER:
				roles.add(Role.USER);
				break;
			case ORGANIZER:
				roles.add(Role.USER);
				roles.add(Role.ORGANIZER);
				break;
			case ADMINISTRATOR:
				roles.add(Role.USER);
				roles.add(Role.ORGANIZER);
				roles.add(Role.ADMINISTRATOR);
				break;
			default:
				roles.add(Role.USER);
		}

		existingUser.setRoles(roles);

		return userRepository.saveAndFlush(existingUser);
	}

	public void delete(Long id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(value -> userRepository.delete(value));
	}
}
