package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.library.entity.LibrarianEntity;

public interface LibrarianRepository extends JpaRepository<LibrarianEntity, Integer> {

}
