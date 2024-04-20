package az.developia.library.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.dto.BookDTO;
import az.developia.library.entity.BookEntity;
import az.developia.library.repository.BookRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/books")
@CrossOrigin(origins = "*")
public class BookRestController {

	@Autowired
	private BookRepository repository;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public List<BookEntity> getBooks() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public BookEntity getBookById(@PathVariable Integer id) {
		return repository.findById(id).get();
	}

	@PostMapping(path = "/add")
	public BookEntity addBook(@Valid @RequestBody BookDTO dto) {
		BookEntity entity = new BookEntity();
		mapper.map(dto, entity);

		return repository.save(entity);

	}

	@DeleteMapping(path = "/delete/{id}")
	public BookEntity deleteBook(@PathVariable Integer id) {
		Optional<BookEntity> byId = repository.findById(id);
		if (byId.isPresent()) {
			repository.deleteById(id);
			return byId.get();
		}
		return null;
	}

	@PutMapping(path = "/update")
	public BookEntity updateBook(@Valid @RequestBody BookEntity entity) {
		if (entity.getId() == null || entity.getId() <= 0) {

		}

		if (repository.findById(entity.getId()).isPresent()) {
			repository.save(entity);
		} else {

		}

		return repository.save(entity);

	}
}
