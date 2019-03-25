package lt.vu.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_participations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TripParticipation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	private Trip trip;

	@NotNull
	@ManyToOne
	private User participant;

	@Enumerated(EnumType.STRING)
	private TripParticipationStatus status;

	@Setter(AccessLevel.PRIVATE)
	private LocalDateTime createdDateTime;

	@Setter(AccessLevel.PRIVATE)
	private LocalDateTime lastEditDateTime;

	@PrePersist
	protected void onCreate() {
		createdDateTime = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		lastEditDateTime = LocalDateTime.now();
	}
}
