package az.developia.library.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.entity.BookEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.AuthorityRepository;
import az.developia.library.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/books")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookRestController {

	private final BookRepository repository;
	private final ModelMapper mapper;
	private final AuthorityRepository authorityRepository;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public List<BookEntity> getBooks() {

		boolean empty = repository.findAll().isEmpty();
		if (empty) {
			throw new OurRuntimeException(null, "Heç bir tələbə yoxdur!");
		}
		List<BookEntity> allStudents = repository.findAll();

		return allStudents;
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public BookEntity getBookById(@PathVariable Integer id) {
		Optional<BookEntity> byId = repository.findById(id);
		boolean present = byId.isPresent();

		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		if (present != true) {
			throw new OurRuntimeException(null, "Id tapılmadı");
		}
		BookEntity book = byId.get();

		return book;
	}

	@GetMapping("/pagination/begin/{begin}/length/{length}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public List<BookEntity> findPagination(@PathVariable Integer begin, @PathVariable Integer length) {

		if (length > 100) {
			throw new OurRuntimeException(null, "Max 100 kitaba eyni anda baxa bilərsiniz");
		}

		List<BookEntity> pagination = repository.findPagination(begin, length);

		return pagination;
	}

	@PostMapping(path = "/add")
	@PreAuthorize(value = "hasAuthority('ROLE_ADD_BOOK')")
	public BookEntity addBook(@Valid @RequestBody BookEntity entity, BindingResult br) {

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		if (repository.findByName(entity.getName()) != null) {
			throw new OurRuntimeException(br, "Bu name istifade edilir");
		}

		entity.setId(null);
		repository.save(entity);
		return entity;
	}

	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_DELETE_BOOK')")
	public BookEntity deleteBook(@PathVariable Integer id) {
		BookEntity entity = repository.findById(id).get();

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}

		if (repository.findById(id) == null) {
			throw new OurRuntimeException(null, "Kitab tapılmadı!");
		}
		repository.deleteById(id);

		return entity;
	}

	@PutMapping(path = "/update")
	@PreAuthorize(value = "hasAuthority('ROLE_UPDATE_BOOK')")
	public boolean updateBook(@Valid @RequestBody BookEntity entity, BindingResult br) {
		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		if (entity.getId() == null || entity.getId() <= 0) {
			throw new OurRuntimeException(null, "id null olmaz");
		}

		if (repository.findById(entity.getId()).isPresent()) {
			repository.save(entity);
			return true;
		} else {
			throw new OurRuntimeException(null, "bu id tapilmadi");
		}

	}
}
