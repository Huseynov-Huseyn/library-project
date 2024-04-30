package az.developia.library.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 ola biler")
	@Column(length = 40, unique = true)
	private String name;

	@Size(min = 1, max = 6, message = "Kitab max 100 min ola biler")
	private String price;

//	@Past(message = "Keçmiş zaman olmalıdır")
	private LocalDate realaseDate;

}
