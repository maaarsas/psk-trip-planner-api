package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;

import java.util.List;

public interface TripMergeService {
	List<Trip> getTripsMergeableToTrip(Long toTripId);

	Trip mergeTrips(Long toTripId, Long mergeableTripId);
}
