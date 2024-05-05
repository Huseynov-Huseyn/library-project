package az.developia.library.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibrarianDeleteResponse {
	private LibrarianAddResponse librarian;
	private String username;

}