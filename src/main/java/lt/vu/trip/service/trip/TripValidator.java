package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripValidator {

	public void validateTrip(Trip trip) {
		LocalDate currentTime = LocalDate.now();
		if (trip.getStartDate() == null || trip.getEndDate() == null) {
			throw new TripValidationException("Start or end date is not provided");
		}
		if (trip.getEndDate().isBefore(currentTime)) {
			throw new TripValidationException("End date can not be before start date");
		}
		if (trip.getStartDate().isBefore(currentTime)) {
			throw new TripValidationException("Start date can not be before current time");
		}
		if (trip.getStartDate().isAfter(trip.getEndDate())) {
			throw new TripValidationException("Start date can not be after end date");
		}

		if (trip.getFromOffice() == null || trip.getToOffice() == null) {
			throw new TripValidationException("From and to offices are required");
		}
		if (trip.getFromOffice().getId().equals(trip.getToOffice().getId())) {
			throw new TripValidationException("From and to offices can not be same");
		}

		for (TripParticipation participation : trip.getTripParticipations()) {
			if (participation.getParticipant() == null || participation.getParticipant().getId() == null) {
				throw new TripValidationException("Participant must exist and have id");
			}
		}


	}
}
