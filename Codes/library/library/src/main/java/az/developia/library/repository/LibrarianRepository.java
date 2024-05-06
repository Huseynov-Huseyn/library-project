package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import az.developia.library.entity.LibrarianEntity;

@Repository
@Transactional
public interface LibrarianRepository extends JpaRepository<LibrarianEntity, Integer> {

	@Query
	LibrarianEntity findByUsername(String username);

}
