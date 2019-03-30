package lt.vu.trip.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.vu.trip.entity.Trip;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripListResponse implements Serializable {

	private int totalPageCount;

	private long totalResultsCount;

	private List<Trip> results;
}
