package lt.vu.trip.controller;

import lt.vu.trip.entity.TripParticipation;
import lt.vu.trip.entity.response.ErrorBodyResponse;
import lt.vu.trip.service.tripparticipation.TripParticipationService;
import lt.vu.trip.service.tripparticipation.TripParticipationStatusChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_USER")
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
			return new ResponseEntity<>(new ErrorBodyResponse(e), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/{id}/reject")
	public ResponseEntity rejectTripParticipation(@PathVariable("id") Long tripParticipationId) {
		try {
			tripParticipationService.reject(tripParticipationId);
			return new ResponseEntity(null, HttpStatus.OK);
		} catch (TripParticipationStatusChangeException e) {
			return new ResponseEntity<>(new ErrorBodyResponse(e), HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_ORGANIZER")
	@PutMapping("")
	public ResponseEntity updateParticipation(@RequestBody TripParticipation participation) {
		tripParticipationService.update(participation);
		return new ResponseEntity(null, HttpStatus.OK);
	}
}
