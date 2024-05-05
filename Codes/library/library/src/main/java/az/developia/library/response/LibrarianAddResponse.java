package az.developia.library.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LibrarianAddResponse {
	private String name;

	private String surname;

	private String username;

	private String email;
}
