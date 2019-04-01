package lt.vu.trip.service.office;

import lt.vu.trip.entity.Office;
import lt.vu.trip.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfficeServiceImpl implements  OfficeService {

	@Autowired
	private OfficeRepository officeRepository;

	@Override
	public Office getOffice(Long id) {
		return officeRepository.findById(id).get();
	}
}
