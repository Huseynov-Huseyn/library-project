package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import az.developia.library.entity.StudentEntity;

@Repository
//@Transactional
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

	@Query
	StudentEntity findByUsername(String username);

}
