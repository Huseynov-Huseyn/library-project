package az.developia.library.response;

import java.util.List;

import az.developia.library.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {

	private List<StudentEntity> students;
	private String username;

}
