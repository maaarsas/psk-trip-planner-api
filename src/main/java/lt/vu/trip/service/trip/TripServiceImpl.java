package lt.vu.trip.service.trip;

import lt.vu.trip.entity.*;
import lt.vu.trip.entity.exception.TripValidationException;
import lt.vu.trip.entity.response.ErrorType;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.repository.TripRepository;
import lt.vu.trip.service.office.OfficeService;
import lt.vu.trip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository repo;

	@Autowired
	private UserService userService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private TripValidator validator;

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

	public Trip create(Trip tripRequest) {
		validator.validateTrip(tripRequest); // basic validation

		Trip trip = new Trip();
		trip.setTripParticipations(getTripUsers(tripRequest, trip)); // also validates users
		trip.setOrganizer(userService.getCurrentUser());
		trip.setStartDate(tripRequest.getStartDate());
		trip.setEndDate(tripRequest.getEndDate());
		trip.setFromOffice(getOffice(tripRequest.getFromOffice())); // also validates office
		trip.setToOffice(getOffice(tripRequest.getToOffice())); // also validates office
		trip.setOfficeReservations(createOfficeReservation(trip));

		return repo.saveAndFlush(trip);
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

	private List<TripParticipation> getTripUsers(Trip tripRequest, Trip trip) {
		List<TripParticipation> participations = new ArrayList<>();
		for (TripParticipation tripParticipation : tripRequest.getTripParticipations()) {
			User user;
			try {
				user = userService.getUser(tripParticipation.getParticipant().getId());
			} catch (Exception e) {
				throw new TripValidationException(ErrorType.TRIP_VALIDATION_PARTICIPANT_DOES_NOT_EXIST.toString());
			}
			if (user == null) {
				throw new TripValidationException(ErrorType.TRIP_VALIDATION_PARTICIPANT_DOES_NOT_EXIST.toString());
			}
			TripParticipation participation = new TripParticipation();
			participation.setParticipant(user);
			participation.setStatus(TripParticipationStatus.INVITED);
			participation.setTrip(trip);
			participation.setAccommodationStatus(tripParticipation.getAccommodationStatus());
			participation.setCarRentalStatus(tripParticipation.getCarRentalStatus());
			participation.setFlightTicketStatus(tripParticipation.getFlightTicketStatus());
			participation.setAccommodationPrice(tripParticipation.getAccommodationPrice());
			participation.setCarRentalPrice(tripParticipation.getCarRentalPrice());
			participation.setFlightTicketPrice(tripParticipation.getFlightTicketPrice());
			participations.add(participation);
		}

		return participations;
	}

	private List<OfficeReservation> createOfficeReservation(Trip trip) {
		OfficeReservation officeReservation = new OfficeReservation();
		officeReservation.setOffice(trip.getToOffice());
		officeReservation.setReservedCapacity(trip.getTripParticipations().size());
		officeReservation.setTrip(trip);
		return Arrays.asList(officeReservation);
	}

	private Office getOffice(Office officeRequest) {
		Office office;
		try {
			office = officeService.getOffice(officeRequest.getId());
		} catch (Exception e) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_OFFICE_DOES_NOT_EXIST.toString());
		}
		if (office == null || office.isDeleted()) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_OFFICE_DOES_NOT_EXIST.toString());
		}

		return office;
	}
}
