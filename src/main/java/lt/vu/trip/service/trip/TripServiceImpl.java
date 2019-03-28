package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository repo;

	public Page<Trip> getAll(int page, int resultsPerPage) {
		Pageable pageable = getDefaultPageable(page, resultsPerPage);
		Page<Trip> trips = repo.findAll(pageable);
		return trips;
	}

	public boolean createNew(Trip trip) {
		repo.saveAndFlush(trip);
		return true;
	}

	private Pageable getDefaultPageable(int page, int resultsPerPage) {
		// pages here are numbered from 0
		Pageable pageable = PageRequest.of(page - 1, resultsPerPage, Sort.by("startDate").and(Sort.by("endDate")));
		return pageable;
	}
}
