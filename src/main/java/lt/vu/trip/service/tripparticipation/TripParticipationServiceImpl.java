package lt.vu.trip.service.tripparticipation;

import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.TripParticipationStatus;
import lt.vu.trip.entity.user.User;
import lt.vu.trip.repository.TripParticipationRepository;
import lt.vu.trip.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class TripParticipationServiceImpl implements TripParticipationService {

	@Autowired
	private TripParticipationRepository repo;

	@Autowired
	private UserService userService;

	public void accept(Long tripParticipationId) {
		changeStatus(tripParticipationId, TripParticipationStatus.ACCEPTED);
	}

	public void reject(Long tripParticipationId) {
		changeStatus(tripParticipationId, TripParticipationStatus.REJECTED);
	}

	public TripParticipation update(TripParticipation participationRequest) {
		TripParticipation participation = repo.findById(participationRequest.getId()).orElse(null);

		if (participation == null) {
			throw new ResourceNotFoundException();
		}

		participation.setAccommodationPrice(participationRequest.getAccommodationPrice());
		participation.setAccommodationStatus(participationRequest.getAccommodationStatus());
		participation.setCarRentalPrice(participationRequest.getCarRentalPrice());
		participation.setCarRentalStatus(participationRequest.getCarRentalStatus());
		participation.setFlightTicketPrice(participationRequest.getFlightTicketPrice());
		participation.setFlightTicketStatus(participationRequest.getFlightTicketStatus());

		return repo.saveAndFlush(participation);
	}

	public List<TripParticipation> getAll(User user) {
		return repo.findAllByParticipantAndStatusNot(user, TripParticipationStatus.REJECTED);
	}

	private void changeStatus(Long tripParticipationId, TripParticipationStatus newStatus) {
		Optional<TripParticipation> optTripParticipation = repo.findById(tripParticipationId);
		if (!optTripParticipation.isPresent()) {
			throw new ResourceNotFoundException();
		}
		TripParticipation tripParticipation = optTripParticipation.get();
		if (!canCurrentUserChangeTripParticipationStatus(tripParticipation, newStatus)) {
			throw new TripParticipationStatusChangeException();
		}
		tripParticipation.setStatus(newStatus);
		repo.saveAndFlush(tripParticipation);
	}

	private boolean canCurrentUserChangeTripParticipationStatus(TripParticipation tripParticipation, TripParticipationStatus newStatus) {
		User currentUser = this.userService.getCurrentUser();
		if (tripParticipation.getParticipant().getId() != currentUser.getId()) {
			return false;
		}
		if (newStatus == TripParticipationStatus.ACCEPTED
				&& tripParticipation.getStatus() != TripParticipationStatus.INVITED) {
			return false;
		}
		if (newStatus == TripParticipationStatus.REJECTED
				&& (tripParticipation.getStatus() != TripParticipationStatus.INVITED
				&& tripParticipation.getStatus() != TripParticipationStatus.ACCEPTED)) {
			return false;
		}
		if (!tripParticipation.getTrip().getStartDate().isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}
}
