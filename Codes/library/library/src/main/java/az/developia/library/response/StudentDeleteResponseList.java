package az.developia.library.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDeleteResponseList {
	private StudentDeleteResponse student;
	private String username;
}
