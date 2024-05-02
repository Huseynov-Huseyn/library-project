package az.developia.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "librarians")
@Getter
@Setter
public class LibrarianEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "VARCHAR(40)")
	private String name;

	@Column(columnDefinition = "VARCHAR(40)")
	private String surname;

	@Column(columnDefinition = "VARCHAR(20)", unique = true)
	private String username;
}
