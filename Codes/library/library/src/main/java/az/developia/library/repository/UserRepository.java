package az.developia.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import az.developia.library.entity.UserEntity;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {

	@Query(value = "update users set username=?2 where username=?1", nativeQuery = true)
	@Modifying
	void updateMyUsername(String username, String newUsername);

	Optional<UserEntity> findUserByUsername(String username);

}