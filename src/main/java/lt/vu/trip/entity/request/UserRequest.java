package lt.vu.trip.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.vu.trip.entity.user.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	private String name;

	private String surname;

	@Enumerated(EnumType.STRING)
	private List<Role> roles = new ArrayList<>();
}
