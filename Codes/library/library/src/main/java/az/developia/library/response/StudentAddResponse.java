package az.developia.library.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StudentAddResponse {

	private String name;
	private String username;
	private String surname;
	private String email;

}
