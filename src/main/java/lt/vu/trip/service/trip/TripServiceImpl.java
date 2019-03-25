package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository repo;

	public List<Trip> getAll() {
		return repo.findAll();
	}

	public boolean createNew(Trip trip) {
		repo.saveAndFlush(trip);
		return true;
	}
}
