package az.developia.library.controller;

import org.springframework.http.ResponseEntity;
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

import az.developia.library.exception.OurRuntimeException;
import az.developia.library.request.StudentAddRequest;
import az.developia.library.response.StudentDeleteResponseList;
import az.developia.library.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentRestController {

	private final StudentService service;

	@GetMapping
	@PreAuthorize(value = "hasAuthority('ROLE_GET_STUDENT')")
	public ResponseEntity<Object> getStudents() {
		ResponseEntity<Object> response = service.findAll();

		return response;
	};

	@GetMapping(path = "/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_GET_STUDENT')")
	public ResponseEntity<Object> getStudentById(@PathVariable Integer id) {
		if (id <= 0 || id == null) {
			throw new OurRuntimeException(null, "Id'i boş qoymaq olmaz!");
		}

		ResponseEntity<Object> response = service.findById(id);

		return response;
	};

	@PostMapping(path = "/add")
	@PreAuthorize(value = "hasAuthority('ROLE_ADD_STUDENT')")
	public ResponseEntity<Object> addStudent(@Valid @RequestBody StudentAddRequest dto, BindingResult br) {
		dto.setId(null);
		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "Məlumatın tamlığı pozulub");
		}

		ResponseEntity<Object> response = service.add(dto);
		return response;
	};

	@DeleteMapping(path = "/delete/{id}")
	@PreAuthorize(value = "hasAuthority('ROLE_DELETE_STUDENT')")
	public ResponseEntity<StudentDeleteResponseList> deleteStudent(@PathVariable Integer id) {
		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id mütləqdir");
		}
		ResponseEntity<StudentDeleteResponseList> response = service.delete(id);
		return response;
	}

	@PutMapping(path = "/update")
	@PreAuthorize(value = "hasAuthority('ROLE_UPDATE_STUDENT')")
//	burada username deyisimine icaze verme onu ancaq users hissesinden icaze ver
	public ResponseEntity<Object> update(@Valid @RequestBody StudentAddRequest request, BindingResult br) {

		if (br.hasErrors()) {
			throw new OurRuntimeException(br, "melumatlarin tamligi pozulub");
		}

		Integer id = request.getId();

		if (id == null || id <= 0) {
			throw new OurRuntimeException(null, "id null olmaz!");
		}

		ResponseEntity<Object> response = service.update(request);
		return response;
	}

}