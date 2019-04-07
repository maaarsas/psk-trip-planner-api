package lt.vu.trip.service.office;

import lt.vu.trip.entity.Office;
import lt.vu.trip.entity.exception.OfficeValidationException;
import lt.vu.trip.entity.response.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OfficeValidator {

	public void validate(Office office) {
		if (office.getMaxCapacity() < 1) {
			throw new OfficeValidationException(ErrorType.OFFICE_VALIDATION_CAPACITY.toString());
		}
		if (StringUtils.isEmpty(office.getTitle()) || StringUtils.isEmpty(office.getTitle().trim())) {
			throw new OfficeValidationException(ErrorType.OFFICE_VALIDATION_TITLE.toString());
		}
	}


}
