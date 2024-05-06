package az.developia.library.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.UserRepository;
import az.developia.library.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserRestController {

	private final UserRepository repository;
	private final UserService service;
	private final ModelMapper mapper;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_USER')")
	public ResponseEntity<Object> getUsers() {
		ResponseEntity<Object> response = service.findAll();

		return response;
	}

	@GetMapping(path = { "/{username}" })
	public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {

		if (username == null) {
			throw new OurRuntimeException(null, "Istifadəçi adı boş qoymaq olmaz!");
		}

		ResponseEntity<Object> response = service.findByUsername(username);

		return response;

	}

	@GetMapping(path = "/login")
	public void login() {

	}
}
