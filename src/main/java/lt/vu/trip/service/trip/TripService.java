package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import org.springframework.data.domain.Page;

public interface TripService {
	Page<Trip> getAll(int page, int resultsPerPage);

	boolean createNew(Trip trip);
}
