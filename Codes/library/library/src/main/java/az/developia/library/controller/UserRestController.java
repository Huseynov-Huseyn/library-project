package az.developia.library.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.developia.library.entity.UserEntity;
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

	@GetMapping(path = "/login")
	public void login() {

	}
}
