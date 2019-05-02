package lt.vu.trip.controller;

import lt.vu.trip.entity.Office;
import lt.vu.trip.service.office.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/office")
public class OfficeController {

	@Autowired
	private OfficeService service;

	@GetMapping("")
	public ResponseEntity<List<Office>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@PostMapping("")
	public ResponseEntity<Office> create(@RequestBody Office office) {
		return ResponseEntity.ok(service.create(office));
	}

	@PutMapping("")
	public ResponseEntity<Office> update(@RequestBody Office office) {
		return ResponseEntity.ok(service.update(office));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
