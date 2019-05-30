package lt.vu.trip.service.tripparticipation;

import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.user.User;

import java.util.List;

public interface TripParticipationService {
	void accept(Long tripParticipationId);

	void reject(Long tripParticipationId);

	TripParticipation update(TripParticipation participation);

	List<TripParticipation> getAll(User user);
}
