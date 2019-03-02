package lt.vu.trip.repository;

import java.util.Optional;
import lt.vu.trip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
