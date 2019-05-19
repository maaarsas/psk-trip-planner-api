package lt.vu.trip.service.user;

import lt.vu.trip.entity.request.UserRequest;
import lt.vu.trip.entity.user.User;

import java.util.List;

public interface UserService {

	User getCurrentUser();

	List<User> searchUsers(String query);

	User getUser(Long id);

	User create(UserRequest userRequest);

	User updateUserRoles(Long id, UserRequest userRequest);

	void delete(Long id);

	List<User> getAll();
}
