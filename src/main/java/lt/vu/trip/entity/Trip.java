package lt.vu.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lt.vu.trip.entity.user.User;

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

	@OneToMany(mappedBy = "trip", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	@JsonManagedReference
	private List<TripParticipation> tripParticipations = new ArrayList<>();

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	private Office fromOffice;

	@ManyToOne
	private Office toOffice;

	@OneToMany(mappedBy = "trip", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JsonManagedReference
	private List<OfficeReservation> officeReservations = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private TripStatus status = TripStatus.ACTIVE;

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
