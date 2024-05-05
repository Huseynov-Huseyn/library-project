package az.developia.library.controller;

import org.modelmapper.ModelMapper;
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

import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import az.developia.library.request.LibrarianAddRequest;
import az.developia.library.response.LibrarianDeleteResponse;
import az.developia.library.service.LibrarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/librarians")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LibrarianRestController {

	private final LibrarianRepository repository;
	private final LibrarianService service;
	private final UserRepository userRepository;
	private final ModelMapper mapper;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_LIBRARIAN')")
	public ResponseEntity<Object> getLibrarians() {
		ResponseEntity<Object> response = service.findAll();

		return response;

	}

	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_LIBRARIAN')")
	public ResponseEntity<Object> getLibrarian(@PathVariable Integer id) {
		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		ResponseEntity<Object> response = service.findById(id);

		return response;

	}

	@PostMapping(path = "/add")
	@PreAuthorize(value = "hasAuthority('ROLE_ADD_LIBRARIAN')")
	public ResponseEntity<Object> addLibrarian(@Valid @RequestBody LibrarianAddRequest dto, BindingResult br) {
		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "Məlumatın tamlığı pozulub");
		}

		ResponseEntity<Object> response = service.add(dto);
		return response;
	}

	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_DELETE_LIBRARIAN')")
	public ResponseEntity<LibrarianDeleteResponse> deleteLibrarian(@PathVariable Integer id) {
		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}
		ResponseEntity<LibrarianDeleteResponse> deletedLibrarian = service.delete(id);
		return deletedLibrarian;
	}

	@PutMapping(path = "/update")
	@PreAuthorize(value = "hasAuthority('ROLE_UPDATE_LIBRARIAN')")
//	burada username deyisimine icaze verme onu ancaq users hissesinden icaze ver
	public ResponseEntity<Object> updateLibrarian(@RequestBody LibrarianAddRequest request, BindingResult br) {

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		Integer id = request.getId();

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id null olmaz!");
		}

		ResponseEntity<Object> response = service.update(request);
		return response;
	}

}