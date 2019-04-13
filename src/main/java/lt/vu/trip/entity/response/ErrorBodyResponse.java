package lt.vu.trip.entity.response;

import lombok.Data;

@Data
public class ErrorBodyResponse {

	private String errorCode;

	private String errorMessage;

	public ErrorBodyResponse(Exception e) {
		this(e, null);
	}

	public ErrorBodyResponse(Exception e, String errorCode) {
		this.errorCode = (errorCode == null) ? e.getClass().getSimpleName() : errorCode;
		this.errorMessage = e.getMessage();
	}
}
