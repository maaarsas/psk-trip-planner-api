package lt.vu.trip.repository;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TripParticipationRepository extends JpaRepository<TripParticipation, Long>,
		JpaSpecificationExecutor<Trip> {
}
