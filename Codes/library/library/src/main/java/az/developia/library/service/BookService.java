package az.developia.library.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import az.developia.library.entity.BookEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.BookRepository;
import az.developia.library.request.BookAddRequest;
import az.developia.library.response.BookAddResponse;
import az.developia.library.response.BookResponse;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class BookService {
	private final BookRepository repository;
	private final SecurityService securityService;

	private final ModelMapper mapper;

	public ResponseEntity<Object> findAll() {
		List<BookEntity> allBooks = repository.findAll();

		if (allBooks.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir tələbə yoxdur!");
		}

		BookResponse response = new BookResponse();

		response.setBooks(allBooks);
		response.setUsername(securityService.findUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> findById(Integer id) {
		BookEntity FindByIdBook = repository.findById(id)
				.orElseThrow(() -> new OurRuntimeException(null, "Id tapılmadı"));

		return ResponseEntity.ok(FindByIdBook);
	}

	public ResponseEntity<Object> findPagination(Integer begin, Integer length) {
		BookResponse response = new BookResponse();
		List<BookEntity> pagination = repository.findPagination(begin, length);
		response.setBooks(pagination);
		response.setUsername(securityService.findUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> add(@Valid BookAddRequest request) {
		if (repository.findByName(request.getName()) != null) {
			throw new OurRuntimeException(null, "Bu name istifade edilir");
		}
		BookEntity entity = new BookEntity();
		entity.setId(null);
		mapper.map(request, entity);
		repository.save(entity);

		BookAddResponse response = new BookAddResponse();
		mapper.map(entity, response);

		return ResponseEntity.ok(response);

	}

	public ResponseEntity<BookEntity> deleteById(Integer id) {
		BookEntity entity = repository.findById(id).get();

		if (repository.findById(id) == null) {
			throw new OurRuntimeException(null, "Kitab tapılmadı!");
		}

		repository.deleteById(id);

		return ResponseEntity.ok(entity);
	}

	public boolean update(@Valid BookEntity entity) {
		BookEntity byName = repository.findByName(entity.getName());
		if (byName == null) {

			if (repository.findById(entity.getId()).isPresent()) {
				repository.save(entity);
				return true;
			} else {
				throw new OurRuntimeException(null, "bu id tapilmadi");
			}

		} else {
			throw new OurRuntimeException(null, "Bu ad istifadə edilir");
		}
	}

}
