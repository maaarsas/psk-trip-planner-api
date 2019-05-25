package lt.vu.trip.service.tripparticipation;

import lt.vu.trip.entity.TripParticipation;

public interface TripParticipationService {
	void accept(Long tripParticipationId);

	void reject(Long tripParticipationId);

	void update(TripParticipation participation);
}
