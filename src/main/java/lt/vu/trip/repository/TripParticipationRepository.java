package lt.vu.trip.repository;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.TripParticipationStatus;
import lt.vu.trip.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TripParticipationRepository extends JpaRepository<TripParticipation, Long>,
		JpaSpecificationExecutor<Trip> {
	List<TripParticipation> findAllByParticipantAndStatusNot(User participant, TripParticipationStatus status);
}
