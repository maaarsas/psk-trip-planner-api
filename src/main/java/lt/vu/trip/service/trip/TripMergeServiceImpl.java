package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.repository.TripParticipationRepository;
import lt.vu.trip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripMergeServiceImpl implements TripMergeService {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private TripParticipationRepository tripParticipationRepository;

	public List<Trip> getTripsMergeableToTrip(Long toTripId) {
		Trip toTrip = tripRepository.findById(toTripId).orElseThrow(ResourceNotFoundException::new);

		TripSearchCriteria criteria = TripSearchCriteria.builder()
				.fromOfficeId(toTrip.getFromOffice().getId())
				.toOfficeId(toTrip.getToOffice().getId())
				.startDateFrom(toTrip.getStartDate().minusDays(1)) // + - 1 day
				.startDateTo(toTrip.getStartDate().plusDays(1))
				.endDateFrom(toTrip.getStartDate().minusDays(1))
				.endDateTo(toTrip.getStartDate().plusDays(1))
				.build();

		List<Trip> mergeableTrips = tripRepository.findAll(TripSpecifications.findByCriteria(criteria));
		return mergeableTrips;
	}

	public Trip mergeTrips(Long toTripId, Long mergeableTripId) {
		Trip toTrip = tripRepository.findById(toTripId).orElseThrow(ResourceNotFoundException::new);
		Trip mergeableTrip = tripRepository.findById(mergeableTripId).orElseThrow(ResourceNotFoundException::new);
		List<Trip> mergeableTrips = getTripsMergeableToTrip(toTrip.getId());

		if (!mergeableTrips.stream().anyMatch(trip -> trip.getId() == mergeableTrip.getId())) {
			throw new TripMergeException(ErrorType.TRIP_MERGE_NOT_MERGEABLE.toString());
		}
		// move all trip participations
		for (TripParticipation tripParticipation : mergeableTrip.getTripParticipations()) {
			tripParticipation.setTrip(toTrip);
			tripParticipationRepository.save(tripParticipation);
		}
		tripRepository.delete(mergeableTrip);
		return toTrip;
	}
}
