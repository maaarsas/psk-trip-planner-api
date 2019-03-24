package lt.vu.trip.controller;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;

	@GetMapping("")
	public ResponseEntity<List<Trip>> getAllTrips() {
		List<Trip> trips = this.tripService.getAll();
		return ResponseEntity.ok(trips);
	}

	@PostMapping("")
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
		this.tripService.createNew(trip);
		return ResponseEntity.ok(trip);
	}
}
