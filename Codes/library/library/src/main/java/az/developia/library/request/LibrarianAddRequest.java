package az.developia.library.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LibrarianAddRequest {

	@Min(value = 0, message = "Id menfi olmamalıdır")
	private Integer id;

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 simvoldan ibarət olmalıdır")
	private String name;

	@Size(min = 2, max = 40, message = "Soyad min 2 max 40 simvoldan ibarət olmalıdır")
	private String surname;

	@Size(min = 2, max = 20, message = "İstifadəçi adı min 2 max 20 simvoldan ibarət olmalıdır")
	private String username;

	@Size(min = 2, max = 20, message = "Şifrə min 2 max 20 simvoldan ibarət olmalıdır")
	private String password;

	private String email;
}
