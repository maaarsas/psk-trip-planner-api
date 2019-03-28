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

	private LocalDate startDate;

	private LocalDate endDate;

	private Long organizerId;

	private Long participantId;

	private TripParticipationStatus participationStatus;
}
