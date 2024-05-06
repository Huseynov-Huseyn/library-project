package az.developia.library.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import az.developia.library.entity.StudentEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.StudentRepository;
import az.developia.library.repository.UserRepository;
import az.developia.library.request.StudentAddRequest;
import az.developia.library.response.StudentAddResponse;
import az.developia.library.response.StudentDeleteResponseList;
import az.developia.library.response.StudentResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class StudentService {
	private final StudentRepository repository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final SecurityService securityService;
	private final ModelMapper mapper;

	public ResponseEntity<Object> findAll() {
		List<StudentEntity> allStudents = repository.findAll();

		if (allStudents.isEmpty()) {
			throw new OurRuntimeException(null, "Heç bir Şagird yoxdur!");
		}

		StudentResponse response = new StudentResponse();

		response.setStudents(allStudents);
		response.setUsername(securityService.findUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> findById(Integer id) {
		StudentEntity FindByIdBook = repository.findById(id)
				.orElseThrow(() -> new OurRuntimeException(null, "Id tapılmadı"));

		return ResponseEntity.ok(FindByIdBook);
	}

	public ResponseEntity<Object> add(StudentAddRequest dto) {

		if (repository.findByUsername(dto.getUsername()) != null) {
			throw new OurRuntimeException(null, "Bu username istifade edilir");
		}

		StudentEntity entity = new StudentEntity();
		mapper.map(dto, entity);
		repository.save(entity);
		userService.addStudentUser(dto);

		StudentAddResponse response = new StudentAddResponse();
		mapper.map(dto, response);

		return ResponseEntity.ok(response);

	}

	public ResponseEntity<StudentDeleteResponseList> delete(Integer id) {
		Optional<StudentEntity> byId = repository.findById(id);

		if (byId == null) {
			throw new OurRuntimeException(null, "Tələbə tapılmadı!");
		}
		repository.deleteById(id);

		userService.deleteById(byId.get().getUsername());

		StudentDeleteResponseList response = new StudentDeleteResponseList();
		mapper.map(byId.get(), response);
		response.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return ResponseEntity.ok(response);

	}

	public ResponseEntity<Object> update(StudentAddRequest request) {
		Optional<StudentEntity> byId = repository.findById(request.getId());

		if (repository.findById(request.getId()).isPresent() != true) {
			throw new OurRuntimeException(null, "bu id tapilmadi");
		}

		String oldUsername = repository.findById(request.getId()).get().getUsername();

		StudentEntity entity = new StudentEntity();
		mapper.map(request, entity);

		if (repository.findByUsername(request.getUsername()) != null) {
			throw new OurRuntimeException(null, "Bu username istifade edilir");
		} else {
			repository.save(entity);
			userService.updateStudent(request, oldUsername);
		}

		StudentDeleteResponseList response = new StudentDeleteResponseList();
		mapper.map(byId.get(), response);
		response.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return ResponseEntity.ok(response);

	}

}