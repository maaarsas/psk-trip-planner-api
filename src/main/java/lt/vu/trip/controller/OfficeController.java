package lt.vu.trip.controller;

import lt.vu.trip.entity.Office;
import lt.vu.trip.service.office.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/office")
public class OfficeController {

	@Autowired
	private OfficeService service;

	@GetMapping("")
	public ResponseEntity<List<Office>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@Secured({"ROLE_ADMINISTRATOR"})
	@PostMapping("")
	public ResponseEntity<Office> create(@RequestBody Office office) {
		return ResponseEntity.ok(service.create(office));
	}

	@Secured({"ROLE_ADMINISTRATOR"})
	@PutMapping("")
	public ResponseEntity<Office> update(@RequestBody Office office) {
		return ResponseEntity.ok(service.update(office));
	}

	@Secured({"ROLE_ADMINISTRATOR"})
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
