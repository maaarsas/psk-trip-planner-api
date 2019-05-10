package lt.vu.trip.service.trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.vu.trip.entity.TripParticipationStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripSearchCriteria {

	private LocalDate startDateFrom;

	private LocalDate startDateTo;

	private LocalDate endDateFrom;

	private LocalDate endDateTo;

	private Long organizerId;

	private Long participantId;

	private TripParticipationStatus participationStatus;

	private Long fromOfficeId;

	private Long toOfficeId;
}
