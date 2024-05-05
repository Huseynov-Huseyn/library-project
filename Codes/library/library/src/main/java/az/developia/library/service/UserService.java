package az.developia.library.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import az.developia.library.entity.UserEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.AuthorityRepository;
import az.developia.library.repository.BookRepository;
import az.developia.library.repository.LibrarianRepository;
import az.developia.library.repository.UserRepository;
import az.developia.library.request.LibrarianAddRequest;
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

		System.out.println("--------------------------------");
		System.out.println(newUsername);
		System.out.println(oldUsername);
		System.out.println("--------------------------------");
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

}
