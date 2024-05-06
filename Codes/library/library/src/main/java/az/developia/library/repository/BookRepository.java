package az.developia.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import az.developia.library.entity.BookEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

	BookEntity findByName(String name);

	@Query(value = "SELECT * FROM library.books limit ?1,?2", nativeQuery = true)
	List<BookEntity> findPagination(Integer begin, Integer length);

//	@Query(value = "SELECT COUNT(*) AS say FROM books WHERE name = ?1", nativeQuery = true)
//	String isNamePresent(String name);

}
