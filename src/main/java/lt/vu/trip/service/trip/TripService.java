package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import org.springframework.data.domain.Page;

public interface TripService {
	Page<Trip> getAll(int page, int resultsPerPage, TripSearchCriteria criteria);

	Page<Trip> getOrganizedByCurrentUser(int page, int resultsPerPage, TripSearchCriteria criteria);

	Page<Trip> getCurrentUserParticipatingIn(int page, int resultsPerPage, TripSearchCriteria criteria);

	Page<Trip> getCurrentUserInvitedIn(int page, int resultsPerPage, TripSearchCriteria criteria);

	Trip create(Trip trip);

	Trip update(Trip tripRequest);
}
