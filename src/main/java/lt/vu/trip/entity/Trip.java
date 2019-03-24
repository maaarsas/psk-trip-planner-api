package lt.vu.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User organizer;

	@OneToMany(mappedBy = "trip")
	private List<TripParticipation> tripParticipations = new ArrayList<>();

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	private Office fromOffice;

	@ManyToOne
	private Office toOffice;

	// TODO: add office reservations

	@Enumerated(EnumType.STRING)
	private TripTaskStatus flightTicketStatus;

	@Enumerated(EnumType.STRING)
	private TripTaskStatus carRentalStatus;

	@Enumerated(EnumType.STRING)
	private TripTaskStatus accomodationStatus;

	@Setter(AccessLevel.PRIVATE)
	private LocalDateTime createdDateTime;

	@Setter(AccessLevel.PRIVATE)
	private LocalDateTime lastEditDateTime;

	@PrePersist
	protected void onCreate() {
		this.createdDateTime = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastEditDateTime = LocalDateTime.now();
	}
}
