package lt.vu.trip.service.tripparticipation;

public interface TripParticipationService {
	void accept(Long tripParticipationId);

	void reject(Long tripParticipationId);
}
