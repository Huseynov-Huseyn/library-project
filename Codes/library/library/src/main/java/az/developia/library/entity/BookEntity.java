package az.developia.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min = 2, max = 40, message = "Ad min 2 max 40 ola biler")
	@Column(length = 40)
	private String name;

	@Size(min = 1, max = 9, message = "Kitab max 1 milyard ola biler")
	private String price;

	@Column(columnDefinition = "INT")
	private Integer page_count;

	@Column(columnDefinition = "TEXT")
	private Integer authorId;

}
