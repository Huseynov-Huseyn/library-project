package az.developia.library.controller;

import org.springframework.http.ResponseEntity;
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
import az.developia.library.request.BookAddRequest;
import az.developia.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/books")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookRestController {
	private final BookService service;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public ResponseEntity<Object> getBooks() {
		ResponseEntity<Object> response = service.findAll();

		return response;
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public ResponseEntity<Object> getBookById(@PathVariable Integer id) {
		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		ResponseEntity<Object> response = service.findById(id);

		return response;
	}

	@GetMapping("/pagination/begin/{begin}/length/{length}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_BOOK')")
	public ResponseEntity<Object> findPagination(@PathVariable Integer begin, @PathVariable Integer length) {

		if (length > 100) {
			throw new OurRuntimeException(null, "Max 100 kitaba eyni anda baxa bilərsiniz");
		}

		ResponseEntity<Object> PaginationResponse = service.findPagination(begin, length);

		return PaginationResponse;
	}

	@PostMapping(path = "/add")
	@PreAuthorize(value = "hasAuthority('ROLE_ADD_BOOK')")
	public ResponseEntity<Object> addBook(@Valid @RequestBody BookAddRequest request, BindingResult br) {

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}
//		
		ResponseEntity<Object> response = service.add(request);
		return response;

	}

	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_DELETE_BOOK')")
	public ResponseEntity<BookEntity> deleteBook(@PathVariable Integer id) {
		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}
		ResponseEntity<BookEntity> response = service.deleteById(id);

		return response;
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
		return service.update(entity);
	}
}
