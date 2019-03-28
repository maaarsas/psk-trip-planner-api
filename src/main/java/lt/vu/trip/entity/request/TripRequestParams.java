package lt.vu.trip.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequestParams implements Serializable {

	@NotNull
	private int page = 1;

	@NotNull
	private int resultsPerPage = 10;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
}
