package lt.vu.trip.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "office_reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfficeReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	private Office office;

	@NotNull
	@ManyToOne
	@JsonBackReference
	private Trip trip;

	private Integer reservedCapacity;

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
