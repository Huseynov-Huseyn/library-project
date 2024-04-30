package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.library.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

	BookEntity findByName(String name);

//	@Query(value = "SELECT COUNT(*) AS say FROM books WHERE name = ?1", nativeQuery = true)
//	String isNamePresent(String name);

}
