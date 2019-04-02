package lt.vu.trip.service.office;

import lt.vu.trip.entity.Office;
import lt.vu.trip.entity.exception.OfficeValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OfficeValidator {

	public void validateNew(Office office) {
		if (office.getId() != null) {
			throw new OfficeValidationException("New office can not have an id");
		}
		if (office.isDeleted()) {
			throw new OfficeValidationException("New office can not be already deleted");
		}
		validateOld(office);
	}

	public void validateOld(Office office) {
		if (office.getMaxCapacity() > 1) {
			throw new OfficeValidationException("Capacity must be a positive integer");
		}
		if (StringUtils.isEmpty(office.getTitle()) || StringUtils.isEmpty(office.getTitle().trim())) {
			throw new OfficeValidationException("Title must contain something");
		}
	}


}
