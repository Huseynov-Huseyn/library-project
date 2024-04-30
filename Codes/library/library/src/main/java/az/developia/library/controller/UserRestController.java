package az.developia.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
public class UserRestController {

//	@Autowired
//	private UserRepository repository;
//
//	@Autowired
//	private LibrarianRepository librarianRepository;
//
//	@Autowired
//	private ModelMapper mapper;
//
//	@GetMapping
//	public List<UserEntity> getUsers() {
//		return repository.findAll();
//	}
//
//	@GetMapping(path = "/login")
//	public void login() {
//
//	}
//
//	@PostMapping(path = "/librarian")
////	public boolean addUser(@Valid @RequestBody LibrarianDTO libdto) {
////
////		LibrarianEntity libentity = new LibrarianEntity();
////		mapper.map(libdto, libentity);
////		librarianRepository.save(libentity);
////
////		UserEntity entity = new UserEntity();
////		mapper.map(libdto, entity);
////		entity.setEnabled(1);
////		entity.setType("Librarian");
////		repository.save(entity);
////
////		return true;
//	}
}
