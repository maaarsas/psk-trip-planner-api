package lt.vu.trip.controller;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.service.trip.TripMergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_ADMINISTRATOR", "ROLE_ORGANIZER"})
@RestController
@RequestMapping("/trip")
public class TripMergeController {

	@Autowired
	private TripMergeService tripMergeService;

	@GetMapping("/{id}/mergeableTrips")
	public ResponseEntity<List<Trip>> getMergeableTrips(@PathVariable("id") Long toTripId) {
		List<Trip> mergeableTrips = tripMergeService.getTripsMergeableToTrip(toTripId);
		return ResponseEntity.ok(mergeableTrips);
	}

	@PostMapping("/{id}/mergeTrip/{mergeId}")
	public ResponseEntity<Trip> mergeTrip(@PathVariable("id") Long toTripId, @PathVariable("mergeId") Long mergeId) {
		Trip trip = tripMergeService.mergeTrips(toTripId, mergeId);
		return ResponseEntity.ok(trip);
	}
}
