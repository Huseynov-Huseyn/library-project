package az.developia.library.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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

import az.developia.library.dto.StudentDTO;
import az.developia.library.entity.StudentEntity;
import az.developia.library.entity.UserEntity;
import az.developia.library.exception.OurRuntimeException;
import az.developia.library.repository.StudentRepository;
import az.developia.library.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentRestController {

	private final StudentRepository repository;
	private final UserRepository userRepository;
	private final ModelMapper mapper;

	@GetMapping
	private List<StudentEntity> getStudents() {
		boolean empty = repository.findAll().isEmpty();
		if (empty) {
			throw new OurRuntimeException(null, "Heç bir tələbə yoxdur!");
		}
		List<StudentEntity> allStudents = repository.findAll();

		return allStudents;
	};

	@GetMapping(path = "/{id}")
	private StudentEntity getStudentById(@PathVariable Integer id) {
		Optional<StudentEntity> byId = repository.findById(id);

		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		if (byId == null) {
			throw new OurRuntimeException(null, "Id tapılmadı");
		}

		StudentEntity Student = byId.get();

		return Student;
	};

	@PostMapping(path = "/add")
	private StudentEntity addStudent(@Valid @RequestBody StudentDTO dto, BindingResult br) {
		dto.setId(null);

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		if (repository.findByUsername(dto.getUsername()) != null) {
			throw new OurRuntimeException(br, "Bu username istifade edilir");
		} else {

			StudentEntity entity = new StudentEntity();
			mapper.map(dto, entity);
			repository.save(entity);

			UserEntity userEntity = new UserEntity();
			userEntity.setEnabled(1);
			userEntity.setType("Student");
			mapper.map(dto, userEntity);
			userRepository.save(userEntity);

			return entity;
		}

	};

	@DeleteMapping(path = "/delete/{id}")
	public StudentEntity deleteStudent(@PathVariable Integer id) {
		StudentEntity entity = repository.findById(id).get();

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}

		if (repository.findById(id) == null) {
			throw new OurRuntimeException(null, "Tələbə tapılmadı!");
		}
		repository.deleteById(id);
		userRepository.deleteById(entity.getUsername());

		return entity;
	}

	@PutMapping(path = "/update")
//	burada username deyisimine icaze verme onu ancaq users hissesinden icaze ver
	public void update(@RequestBody StudentEntity requestEntity, BindingResult br) {

		Integer id = requestEntity.getId();
		StudentEntity entity = new StudentEntity();

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id null olmaz");
		}

		if (repository.findByUsername(requestEntity.getUsername()) != null) {
			throw new OurRuntimeException(br, "Bu username istifade edilir");
		} else {
			if (repository.findById(id).isPresent()) {
				repository.save(entity);
			} else {
				throw new OurRuntimeException(null, "bu id tapilmadi");
			}
			repository.save(entity);
		}
	}

}