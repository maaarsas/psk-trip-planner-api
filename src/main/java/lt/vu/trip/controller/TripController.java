package lt.vu.trip.controller;

import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.request.TripRequestParams;
import lt.vu.trip.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

	@Autowired
	private TripService tripService;

	@GetMapping("")
	public ResponseEntity<List<Trip>> getAllTrips(@Valid TripRequestParams requestParams) {
		Page<Trip> trips = tripService.getAll(requestParams.getPage(), requestParams.getResultsPerPage());
		return ResponseEntity.ok(trips.getContent());
	}

	@PostMapping("")
	public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
		tripService.createNew(trip);
		return ResponseEntity.ok(trip);
	}
}
