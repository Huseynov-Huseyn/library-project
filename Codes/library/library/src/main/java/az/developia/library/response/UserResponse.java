package az.developia.library.response;

import java.util.List;

import az.developia.library.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	private List<UserEntity> Users;
	private String username;

}