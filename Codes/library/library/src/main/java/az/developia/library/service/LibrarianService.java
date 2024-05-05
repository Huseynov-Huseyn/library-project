package az.developia.library.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import az.developia.library.entity.LibrarianEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.request.LibrarianAddRequest;
import az.developia.library.response.LibrarianAddResponse;
import az.developia.library.response.LibrarianDeleteResponse;
import az.developia.library.response.LibrarianResponse;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class LibrarianService {
	private final LibrarianRepository repository;
	private final UserService userService;
	private final SecurityService securityService;
	private final ModelMapper mapper;

	public ResponseEntity<Object> findAll() {
		List<LibrarianEntity> allLibrarians = repository.findAll();

		if (allLibrarians.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir Kitabxanaçı yoxdur!");
		}

		LibrarianResponse response = new LibrarianResponse();

		response.setLibrarians(allLibrarians);
		response.setUsername(securityService.findUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> findById(Integer id) {
		LibrarianEntity FindByIdBook = repository.findById(id)
				.orElseThrow(() -> new OurRuntimeException(null, "Id tapılmadı"));

		return ResponseEntity.ok(FindByIdBook);
	}

	public ResponseEntity<Object> add(@Valid LibrarianAddRequest dto) {

		if (repository.findByUsername(dto.getUsername()) != null) {
			throw new OurRuntimeException(null, "Bu username istifade edilir");
		}

		LibrarianEntity entity = new LibrarianEntity();
		mapper.map(dto, entity);
		repository.save(entity);
		userService.addLibrarianUser(dto);

		LibrarianAddResponse response = new LibrarianAddResponse();
		mapper.map(dto, response);

		return ResponseEntity.ok(response);

	}

	public ResponseEntity<LibrarianDeleteResponse> delete(Integer id) {
		Optional<LibrarianEntity> byId = repository.findById(id);
		if (byId == null) {
			throw new OurRuntimeException(null, "Kitabxanaçı tapılmadı!");
		}
		repository.deleteById(id);

		userService.deleteById(byId.get().getUsername());

		LibrarianDeleteResponse response = new LibrarianDeleteResponse();
		mapper.map(byId.get(), response);
		return ResponseEntity.ok(response);

	}

	public ResponseEntity<Object> update(LibrarianAddRequest request) {
		Optional<LibrarianEntity> byId = repository.findById(request.getId());

		if (repository.findById(request.getId()).isPresent() != true) {
			throw new OurRuntimeException(null, "bu id tapilmadi");
		}
		String oldUsername = repository.findById(request.getId()).get().getUsername();

		LibrarianEntity entity = new LibrarianEntity();
		mapper.map(request, entity);

		if (repository.findByUsername(request.getUsername()) != null) {
			throw new OurRuntimeException(null, "Bu username istifade edilir");
		} else {
			repository.save(entity);
			userService.updateLibrarian(request, oldUsername);
		}

		LibrarianDeleteResponse response = new LibrarianDeleteResponse();
		mapper.map(byId.get(), response);
		return ResponseEntity.ok(response);

	}

}