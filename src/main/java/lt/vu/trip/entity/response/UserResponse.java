package lt.vu.trip.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.trip.entity.user.Role;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private Long id;

	private String name;

	private String surname;

	private List<Role> roles;
}
