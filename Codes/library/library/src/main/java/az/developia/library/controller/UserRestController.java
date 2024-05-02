package az.developia.library.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.entity.UserEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserRestController {

	private final UserRepository repository;
	private final LibrarianRepository librarianRepository;
	private final ModelMapper mapper;

	@GetMapping
	public List<UserEntity> getUsers() {

		List<UserEntity> allUsers = repository.findAll();
		if (allUsers.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir istifadəçi mövcud deyil");
		}

		return allUsers;
	}

	@GetMapping(path = { "/{username}" })
	public UserEntity getUserByUsername(@PathVariable String username) {
		Optional<UserEntity> userByUsername = repository.findUserByUsername(username);
		UserEntity response = new UserEntity();

		repository.findUserByUsername(username).ifPresentOrElse(o -> {
			mapper.map(userByUsername.get(), response);
		}, () -> {
			throw new OurRuntimeException(null, "Bu Istifadəçi adı yoxdur");
		});

		return response;
	}

//	@PostMapping(path = "/librarian")
//	public boolean addUser(@Valid @RequestBody LibrarianDTO libdto) {
//
//		LibrarianEntity libentity = new LibrarianEntity();
//		mapper.map(libdto, libentity);
//		librarianRepository.save(libentity);
//
//		UserEntity entity = new UserEntity();
//		mapper.map(libdto, entity);
//		entity.setEnabled(1);
//		entity.setType("Librarian");
//		repository.save(entity);
//
//		return true;
//	}

	@GetMapping(path = "/login")
	public void login() {

	}
}
