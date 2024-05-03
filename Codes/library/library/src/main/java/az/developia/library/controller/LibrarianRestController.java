package az.developia.library.controller;

import java.util.List;

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

import az.developia.library.dto.LibrarianDTO;
import az.developia.library.entity.LibrarianEntity;
import az.developia.library.entity.UserEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/librarians")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LibrarianRestController {

	private final LibrarianRepository repository;
	private final UserRepository userRepository;
	private final ModelMapper mapper;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_LIBRARIAN')")
	public List<LibrarianEntity> getLibrarians() {

		List<LibrarianEntity> allLibrarians = repository.findAll();
		if (allLibrarians.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir kitabxanaçı mövcud deyil");
		}

		return allLibrarians;
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_LIBRARIAN')")
	public LibrarianEntity getLibrarian(@PathVariable Integer id) {
		boolean present = repository.findById(id).isPresent();

		if (present != true) {
			throw new OurRuntimeException(null, "Id tapılmadı");
		}

		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		LibrarianEntity Librarian = repository.findById(id).get();
		return Librarian;

	}

	@PostMapping(path = "/add")
	@PreAuthorize(value = "hasAuthority('ROLE_ADD_LIBRARIAN')")
	public LibrarianEntity addLibrarian(@Valid @RequestBody LibrarianDTO dto, BindingResult br) {
		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "Məlumatın tamlığı pozulub");
		}

		if (repository.findByUsername(dto.getUsername()) != null) {
			throw new OurRuntimeException(br, "Bu username istifade edilir");
		}

		LibrarianEntity entity = new LibrarianEntity();
		mapper.map(dto, entity);
		repository.save(entity);

		UserEntity userEntity = new UserEntity();
		userEntity.setEnabled(1);
		userEntity.setType("Librarian");
		mapper.map(dto, userEntity);
		userRepository.save(userEntity);

		return entity;
	}

	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_DELETE_LIBRARIAN')")
	public LibrarianEntity deleteLibrarian(@PathVariable Integer id) {
		LibrarianEntity entity = repository.findById(id).get();

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}

		if (repository.findById(id) == null) {
			throw new OurRuntimeException(null, "Kitabxanaçı tapılmadı!");
		}
		repository.deleteById(id);
		userRepository.deleteById(entity.getUsername());

		return entity;
	}

	@PutMapping(path = "/update")
	@PreAuthorize(value = "hasAuthority('ROLE_UPDATE_LIBRARIAN')")
//	burada username deyisimine icaze verme onu ancaq users hissesinden icaze ver
	public void updateLibrarian(@RequestBody LibrarianDTO requestDTO, BindingResult br) {
		Integer id = requestDTO.getId();

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id null olmaz!");
		}
		if (repository.findById(id).isPresent() != true) {
			throw new OurRuntimeException(null, "bu id tapilmadi");
		}

		LibrarianEntity entity = new LibrarianEntity();
		mapper.map(requestDTO, entity);

		if (repository.findByUsername(requestDTO.getUsername()) != null) {
			throw new OurRuntimeException(br, "Bu username istifade edilir");
		} else {
			repository.save(entity);
		}

	}

}