package lt.vu.trip.controller;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.request.TripRequestParams;
import lt.vu.trip.entity.response.TripListResponse;
import lt.vu.trip.service.trip.TripSearchCriteria;
import lt.vu.trip.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;

	@GetMapping("")
	public ResponseEntity<TripListResponse> getAllTrips(@Valid TripRequestParams requestParams) {
		TripSearchCriteria criteria = buildSearchCriteriaFromRequest(requestParams);
		Page<Trip> trips = tripService.getAll(requestParams.getPage(), requestParams.getResultsPerPage(), criteria);
		TripListResponse response = buildListResponseFromPage(trips);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/my")
	public ResponseEntity<TripListResponse> getCurrentUserTrips(@Valid TripRequestParams requestParams) {
		TripSearchCriteria criteria = buildSearchCriteriaFromRequest(requestParams);
		Page<Trip> trips = tripService.getCurrentUserParticipatingIn(
				requestParams.getPage(), requestParams.getResultsPerPage(), criteria);
		TripListResponse response = buildListResponseFromPage(trips);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/invitation")
	public ResponseEntity<TripListResponse> getInvitations(@Valid TripRequestParams requestParams) {
		TripSearchCriteria criteria = buildSearchCriteriaFromRequest(requestParams);
		Page<Trip> trips = tripService.getCurrentUserInvitedIn(
				requestParams.getPage(), requestParams.getResultsPerPage(), criteria);
		TripListResponse response = buildListResponseFromPage(trips);
		return ResponseEntity.ok(response);
	}

	@Secured({"ROLE_ADMINISTRATOR", "ROLE_ORGANIZER"})
	@GetMapping("/organized")
	public ResponseEntity<TripListResponse> getOrganized(@Valid TripRequestParams requestParams) {
		TripSearchCriteria criteria = buildSearchCriteriaFromRequest(requestParams);
		Page<Trip> trips = tripService.getOrganizedByCurrentUser(requestParams.getPage(), requestParams.getResultsPerPage(), criteria);
		TripListResponse response = buildListResponseFromPage(trips);
		return ResponseEntity.ok(response);
	}

	@Secured({"ROLE_ADMINISTRATOR", "ROLE_ORGANIZER"})
	@PostMapping("")
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
		return ResponseEntity.ok(tripService.create(trip));
	}

	private TripSearchCriteria buildSearchCriteriaFromRequest(TripRequestParams requestParams) {
		return TripSearchCriteria.builder()
				.startDateFrom(requestParams.getStartDateFrom())
				.startDateTo(requestParams.getStartDateTo())
				.endDateFrom((requestParams.getEndDateFrom()))
				.endDateTo((requestParams.getEndDateTo()))
				.build();
	}

	private TripListResponse buildListResponseFromPage(Page<Trip> trips) {
		return TripListResponse.builder()
				.totalPageCount(trips.getTotalPages())
				.totalResultsCount(trips.getTotalElements())
				.results(trips.getContent())
				.build();
	}
}
