package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import az.developia.library.entity.AuthorityEntity;

@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

	@Query(value = "insert into authorities (username,authority) select ?1,authority from authority_list where admin=1", nativeQuery = true)
	@Modifying
	void addAdminAut(String username);

	@Query(value = "insert into authorities (username,authority) select ?1,authority from authority_list where librarian=1", nativeQuery = true)
	@Modifying
	void addLibrarianAut(String username);

	@Query(value = "insert into authorities (username,authority) select ?1,authority from authority_list where student=1", nativeQuery = true)
	@Modifying
	void addStudentAut(String username);

	@Query(value = "delete from authorities where username=?1", nativeQuery = true)
	@Modifying
	void deleteUserAuthorities(String username);

	@Query(value = "update authorities set username=?1 where username=?2", nativeQuery = true)
	@Modifying
	void updateUserUsername(String newU, String oldU);

}
