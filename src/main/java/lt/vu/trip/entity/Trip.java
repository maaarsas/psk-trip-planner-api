package lt.vu.trip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	private LocalDateTime createdDateTime;

	private LocalDateTime lastEditDateTime;
}
