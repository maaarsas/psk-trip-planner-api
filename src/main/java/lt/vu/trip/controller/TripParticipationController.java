package lt.vu.trip.controller;

import lt.vu.trip.service.tripparticipation.TripParticipationService;
import lt.vu.trip.service.tripparticipation.TripParticipationStatusChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tripParticipation")
public class TripParticipationController {

	@Autowired
	private TripParticipationService tripParticipationService;

	@PostMapping("/{id}/accept")
	public ResponseEntity acceptTripParticipation(@PathVariable("id") Long tripParticipationId) {
		try {
			tripParticipationService.accept(tripParticipationId);
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (TripParticipationStatusChangeException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/{id}/reject")
	public ResponseEntity rejectTripParticipation(@PathVariable("id") Long tripParticipationId) {
		try {
			tripParticipationService.reject(tripParticipationId);
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (TripParticipationStatusChangeException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
