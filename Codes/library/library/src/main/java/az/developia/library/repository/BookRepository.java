package az.developia.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import az.developia.library.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

}
