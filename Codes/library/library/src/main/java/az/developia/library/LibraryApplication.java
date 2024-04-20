package az.developia.library;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper obj = new ModelMapper();
		return obj;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
		System.out.println("------------------------------");
		System.out.println("Sir,library project is woking!");
		System.out.println("------------------------------");
	}

}
