package az.developia.library.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import az.developia.library.entity.UserEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.AuthorityRepository;
import az.developia.library.repository.BookRepository;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import az.developia.library.request.LibrarianAddRequest;
import az.developia.library.request.StudentAddRequest;
import az.developia.library.response.UserResponse;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final AuthorityRepository authorityRepository;
	private final SecurityService securityService;
	private final ModelMapper mapper;
	private final BookRepository bookRepository;
	private final LibrarianRepository librarianRepository;

	public ResponseEntity<Object> findAll() {
		List<UserEntity> allUsers = repository.findAll();

		if (allUsers.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir Istifadəçi yoxdur!");
		}

		UserResponse response = new UserResponse();

		response.setUsers(allUsers);
		response.setUsername(securityService.findUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> findByUsername(String username) {
		Optional<UserEntity> userByUsername = repository.findUserByUsername(username);
		UserResponse response = new UserResponse();

		repository.findUserByUsername(username).ifPresentOrElse(o -> {
			mapper.map(userByUsername.get(), response);
		}, () -> {
			throw new OurRuntimeException(null, "Bu Istifadəçi adı yoxdur");
		});

		return ResponseEntity.ok(response);
	}

	public boolean addLibrarianUser(@Valid LibrarianAddRequest dto) {
		if (repository.findById(dto.getUsername()).isPresent() != true) {
			UserEntity userEntity = new UserEntity();
			userEntity.setEnabled(1);
			userEntity.setType("Librarian");
			mapper.map(dto, userEntity);
			authorityRepository.addLibrarianAut(dto.getUsername());
			repository.save(userEntity);
		} else {
			throw new OurRuntimeException(null, "Username istifadə edilir!");
		}
		return true;

	}

	public boolean deleteById(String username) {
		if (repository.findById(username).isPresent()) {
			repository.deleteById(username);
			authorityRepository.deleteUserAuthorities(username);
		} else {
			throw new OurRuntimeException(null, "Belə username mövcud deyil");
		}

		return true;
	}

	public Optional<UserEntity> findById(String username) {
		return repository.findById(username);

	}

	public boolean updateLibrarian(LibrarianAddRequest request, String oldUsername) {
		String newUsername = request.getUsername();

		UserEntity entity = new UserEntity();
		mapper.map(request, entity);
		entity.setEnabled(1);
		entity.setType("Librarian");

		if (repository.findById(oldUsername).isPresent()) {
			repository.updateMyUsername(oldUsername, newUsername);
			repository.save(entity);
			authorityRepository.updateUserUsername(newUsername, oldUsername);
		} else {
			throw new OurRuntimeException(null, "Belə username mövcud deyil");
		}

		return true;
	}

	public boolean addStudentUser(@Valid StudentAddRequest dto) {

		if (repository.findById(dto.getUsername()).isPresent() != true) {
			UserEntity userEntity = new UserEntity();
			userEntity.setEnabled(1);
			userEntity.setType("Student");
			mapper.map(dto, userEntity);
			authorityRepository.addStudentAut(dto.getUsername());
			repository.save(userEntity);
		} else {
			throw new OurRuntimeException(null, "Username istifadə edilir!");
		}
		return true;

	}

	public boolean updateStudent(StudentAddRequest request, String oldUsername) {
		String newUsername = request.getUsername();

		UserEntity entity = new UserEntity();
		mapper.map(request, entity);
		entity.setEnabled(1);
		entity.setType("Student");

		if (repository.findById(oldUsername).isPresent()) {
			repository.updateMyUsername(oldUsername, newUsername);
			repository.save(entity);
			authorityRepository.updateUserUsername(newUsername, oldUsername);
		} else {
			throw new OurRuntimeException(null, "Belə username mövcud deyil");
		}

		return true;

	}

}
