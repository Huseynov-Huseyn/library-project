package az.developia.library.response;

import java.util.List;

import az.developia.library.entity.LibrarianEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibrarianResponse {
	private List<LibrarianEntity> librarians;
	private String username;

}
