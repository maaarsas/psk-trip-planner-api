package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipationStatus;
import lt.vu.trip.entity.User;
import lt.vu.trip.repository.TripRepository;
import lt.vu.trip.service.user.UserService;
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

	@Autowired
	private UserService userService;

	public Page<Trip> getAll(int page, int resultsPerPage, TripSearchCriteria criteria) {
		return this.getList(page, resultsPerPage, criteria);
	}

	public Page<Trip> getOrganizedByCurrentUser(int page, int resultsPerPage, TripSearchCriteria criteria) {
		User currentUser = userService.getCurrentUser();
		criteria.setOrganizerId(currentUser.getId());
		return this.getList(page, resultsPerPage, criteria);
	}

	public Page<Trip> getCurrentUserParticipatingIn(int page, int resultsPerPage, TripSearchCriteria criteria) {
		User currentUser = userService.getCurrentUser();
		criteria.setParticipantId(currentUser.getId());
		criteria.setParticipationStatus(TripParticipationStatus.ACCEPTED);
		return this.getList(page, resultsPerPage, criteria);
	}

	public Page<Trip> getCurrentUserInvitedIn(int page, int resultsPerPage, TripSearchCriteria criteria) {
		User currentUser = userService.getCurrentUser();
		criteria.setParticipantId(currentUser.getId());
		criteria.setParticipationStatus(TripParticipationStatus.INVITED);
		return this.getList(page, resultsPerPage, criteria);
	}

	public boolean createNew(Trip trip) {
		repo.saveAndFlush(trip);
		return true;
	}

	private Page<Trip> getList(int page, int resultsPerPage, TripSearchCriteria criteria) {
		Pageable pageable = getDefaultPageable(page, resultsPerPage);
		Page<Trip> trips = repo.findAll(TripSpecifications.findByCriteria(criteria), pageable);
		return trips;
	}

	private Pageable getDefaultPageable(int page, int resultsPerPage) {
		// pages here are numbered from 0
		Pageable pageable = PageRequest.of(page - 1, resultsPerPage, Sort.by("startDate").and(Sort.by("endDate")));
		return pageable;
	}
}
