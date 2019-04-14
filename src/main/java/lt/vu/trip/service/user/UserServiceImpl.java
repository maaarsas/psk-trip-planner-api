package lt.vu.trip.service.user;

import lt.vu.trip.entity.user.User;
import lt.vu.trip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User getCurrentUser() {
		Long currentUserId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		Optional<User> user = userRepository.findById(currentUserId);
		return user.get();
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
		return userRepository.findById(id).get();
	}

}
