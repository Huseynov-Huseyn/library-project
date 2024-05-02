package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import az.developia.library.entity.LibrarianEntity;

@Repository
public interface LibrarianRepository extends JpaRepository<LibrarianEntity, Integer> {

	@Query
	LibrarianEntity findByUsername(String username);

}
