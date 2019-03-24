package lt.vu.trip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_participations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripParticipation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	private Trip trip;

	@NotNull
	@ManyToOne
	private User participant;

	@Enumerated(EnumType.STRING)
	private TripParticipationStatus status;

	private LocalDateTime createdDateTime;

	private LocalDateTime lastEditDateTime;
}
