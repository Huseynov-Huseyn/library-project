package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.library.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
