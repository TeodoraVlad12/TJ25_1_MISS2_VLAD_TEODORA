package Students.example.Students;

import Students.example.Students.entity.Student;
import Students.example.Students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Student student1 = new Student(1L, "STU001", "John Doe", "john.doe@example.com", 2);
		Student student2 = new Student(2L, "STU002", "Jane Smith", "jane.smith@example.com", 3);
		
		studentRepository.save(student1);
		studentRepository.save(student2);
		
		System.out.println("Students saved successfully!");
		
		System.out.println("All students:");
		studentRepository.findAll().forEach(student -> 
			System.out.println(student.getCode() + " - " + student.getName() + " (Year " + student.getYear() + ")")
		);
		
		System.out.println("Students in year 2:");
		studentRepository.findByYear(2).forEach(student -> 
			System.out.println(student.getCode() + " - " + student.getName())
		);
	}
}
