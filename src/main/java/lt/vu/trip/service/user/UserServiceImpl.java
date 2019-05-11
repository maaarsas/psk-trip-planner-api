package lt.vu.trip.service.user;

import lt.vu.trip.entity.user.Role;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.repository.UserRepository;
import lt.vu.trip.service.auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
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

	public User create(User user) {
		validator.validate(user);
		User newUser = User.builder()
			.username(user.getUsername())
			.password(passwordEncoder.encode(user.getPassword()))
			.surname(user.getSurname())
			.name(user.getName())
			.roles((user.getRoles() == null || user.getRoles().isEmpty()) ? Arrays.asList(Role.USER) : user.getRoles())
			.build();
		userRepository.save(newUser);

		return newUser;
	}

	public User updateUserRoles(User user) {
		User existingUser = userRepository.findById(user.getId()).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException();
		}

		existingUser.setRoles(user.getRoles());

		return userRepository.saveAndFlush(existingUser);
	}
	
	public void delete(Long id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(value -> userRepository.delete(value));
	}
}
