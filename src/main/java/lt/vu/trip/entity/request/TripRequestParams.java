package lt.vu.trip.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequestParams implements Serializable {

	@NotNull
	private int page = 1;

	@NotNull
	private int resultsPerPage = 10;
}
