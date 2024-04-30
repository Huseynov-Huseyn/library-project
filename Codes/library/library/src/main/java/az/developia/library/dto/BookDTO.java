package az.developia.library.dto;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BookDTO {
	@Id
	private Integer id;

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 simvoldan ibarət olmalıdır")
	private String name;

	@Size(min = 1, max = 6, message = "Kitab max 100 min ola biler")
	private String price;

	@Past(message = "Keçmiş zaman olmalıdır")
	private LocalDate realaseDate;

}