package lt.vu.trip.service.office;

import lt.vu.trip.entity.Office;

import java.util.List;

public interface OfficeService {

	Office getOffice(Long id);

	List<Office> getAll();

	Office create(Office office);

	Office update(Office office);

	void delete(Long id);
}
