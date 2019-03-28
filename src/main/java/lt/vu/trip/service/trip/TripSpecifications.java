package lt.vu.trip.service.trip;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TripSpecifications {

	public static Specification<Trip> findByCriteria(final TripSearchCriteria searchCriteria) {
		return new Specification<Trip>() {
			@Override
			public Predicate toPredicate(Root<Trip> trip, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (searchCriteria.getStartDate() != null) {
					predicates.add(cb.greaterThanOrEqualTo(trip.<LocalDate>get("startDate"), searchCriteria.getStartDate()));
				}
				if (searchCriteria.getEndDate() != null) {
					predicates.add(cb.lessThanOrEqualTo(trip.<LocalDate>get("endDate"), searchCriteria.getEndDate()));
				}
				if (searchCriteria.getOrganizerId() != null) {
					final Join<Trip, User> organizer = trip.join("organizer");
					predicates.add(cb.equal(organizer.<Long>get("id"), searchCriteria.getOrganizerId()));
				}
				if (searchCriteria.getParticipantId() != null) {
					final Join<Trip, TripParticipation> participation = trip.join("tripParticipations");
					final Join<TripParticipation, User> user = participation.join("participant");

					predicates.add(cb.equal(user.<Long>get("id"), searchCriteria.getParticipantId()));

					if (searchCriteria.getParticipationStatus() != null) {
						predicates.add(cb.equal(participation.<Long>get("status"), searchCriteria.getParticipationStatus()));
					}
				}
				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		};
	}
}
