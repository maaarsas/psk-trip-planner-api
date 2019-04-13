package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.exception.TripValidationException;
import lt.vu.trip.entity.response.ErrorType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripValidator {

	public void validateTrip(Trip trip) {
		LocalDate currentTime = LocalDate.now();
		if (trip.getStartDate() == null || trip.getEndDate() == null) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_DATE_NOT_PROVIDED.toString());
		}
		if (trip.getEndDate().isBefore(currentTime)) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_DATE_END_BEFORE_NOW.toString());
		}
		if (trip.getStartDate().isBefore(currentTime)) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_DATE_START_BEFORE_NOW.toString());
		}
		if (trip.getStartDate().isAfter(trip.getEndDate())) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_DATE_START_AFTER_END.toString());
		}

		if (trip.getFromOffice() == null || trip.getToOffice() == null) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_OFFICE_REQUIRED.toString());
		}
		if (trip.getFromOffice().getId().equals(trip.getToOffice().getId())) {
			throw new TripValidationException(ErrorType.TRIP_VALIDATION_OFFICE_SAME.toString());
		}

		for (TripParticipation participation : trip.getTripParticipations()) {
			if (participation.getParticipant() == null || participation.getParticipant().getId() == null) {
				throw new TripValidationException(ErrorType.TRIP_VALIDATION_PARTICIPANT_DOES_NOT_EXIST.toString());
			}
		}

	}
}
