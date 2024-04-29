package az.developia.library.dto;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 simvoldan ibarət olmalıdır")
	private String name;

	@Size(min = 1, max = 9, message = "Qiymət max 1 milyar ola bilər")
	private String price;

	private Integer page_count;

	@Past(message = "Keçmiş zaman olmalıdır")
	private LocalDate realaseDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
}