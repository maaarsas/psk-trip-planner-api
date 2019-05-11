package lt.vu.trip.service.user;

import lt.vu.trip.entity.user.User;

import java.util.List;

public interface UserService {

	User getCurrentUser();

	List<User> searchUsers(String query);

	User getUser(Long id);

	User create(User user);

	User updateUserRoles(User user);

	void delete(Long id);
}
