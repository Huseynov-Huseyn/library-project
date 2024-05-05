package az.developia.library.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StudentAddRequest {

	@Min(value = 0, message = "Id menfi olmaz")
	private Integer id;

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 ola biler")
	private String name;

	@Size(min = 2, max = 40, message = "Soyad min 2 max 40 ola biler")
	private String surname;

	@Size(min = 2, max = 20, message = "Username min 2 max 20 ola biler")
	@NotNull
	private String username;

	@Size(min = 2, max = 20, message = "Password min 2 max 20 ola biler")
	@NotNull
	private String password;

//	@Pattern(regexp = "^\\+994(\\d{12})$", message = "nömrə yanlışdır")
	private String phone;

	private String adress;

//	@Pattern(regexp = "[a-z]+@[a-z]+\\.[a-z]{2,4}", message = "emaili duz yaz")
	private String email;

}