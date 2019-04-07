package lt.vu.trip.service.office;

import lt.vu.trip.entity.Office;
import lt.vu.trip.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	private OfficeRepository repository;

	@Autowired
	private OfficeValidator validator;

	@Override
	public Office getOffice(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Office> getAll() {
		return repository.findAllByDeletedFalse();
	}

	@Override
	public Office create(Office officeRequest) {
		validator.validate(officeRequest);
		Office office = new Office();
		office.setTitle(officeRequest.getTitle());
		office.setMaxCapacity(officeRequest.getMaxCapacity());
		return repository.saveAndFlush(office);
	}

	@Override
	public Office update(Office officeRequest) {
		validator.validate(officeRequest);

		Office office = repository.findByIdAndDeletedFalse(officeRequest.getId());
		if (office == null) {
			throw new ResourceNotFoundException("Office not found");
		}

		office.setMaxCapacity(officeRequest.getMaxCapacity());
		office.setTitle(officeRequest.getTitle());

		return repository.saveAndFlush(office);
	}

	@Override
	public void delete(Long id) {
		Optional<Office> office = repository.findById(id);
		if (office.isPresent()) {
			office.get().setDeleted(true);
			repository.saveAndFlush(office.get());
		} else {
			throw new ResourceNotFoundException("Office does not exist");
		}
	}
}
