package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;

import java.util.List;

public interface TripService {
	List<Trip> getAll();

	boolean createNew(Trip trip);
}
