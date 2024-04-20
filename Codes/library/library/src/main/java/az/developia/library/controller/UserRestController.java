package az.developia.library.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.dto.LibrarianDTO;
import az.developia.library.entity.LibrarianEntity;
import az.developia.library.entity.UserEntity;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
public class UserRestController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private LibrarianRepository librarianRepository;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public List<UserEntity> getUsers() {
		return repository.findAll();
	}

	@GetMapping(path = "/login")
	public void login() {

	}

	@PostMapping(path = "/librarian")
	public boolean addUser(@Valid @RequestBody LibrarianDTO libdto) {

		LibrarianEntity libentity = new LibrarianEntity();
		mapper.map(libdto, libentity);
		librarianRepository.save(libentity);

		UserEntity entity = new UserEntity();
		mapper.map(libdto, entity);
		entity.setEnabled(1);
		entity.setType("Librarian");
		repository.save(entity);

		return true;
	}

}
