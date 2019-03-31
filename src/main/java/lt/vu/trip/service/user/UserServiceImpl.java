package lt.vu.trip.service.user;

import lt.vu.trip.entity.User;
import lt.vu.trip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public List<User> searchUsers(String query) {
		String[] queryParts = query.trim().split(" ");
		if (queryParts.length > 1) {
			return userRepository.findTop10ByNameStartingWithIgnoreCaseAndSurnameStartingWithIgnoreCase(queryParts[0], queryParts[1]);
		} else {
			return userRepository.findTop10ByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(query, query);
		}
	}


}
