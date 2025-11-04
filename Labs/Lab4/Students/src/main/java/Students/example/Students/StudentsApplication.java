package Students.example.Students;

import Students.example.Students.entity.*;
import Students.example.Students.entity.Course.CourseType;
import Students.example.Students.service.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class StudentsApplication implements CommandLineRunner {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private PackService packService;
	
	@Autowired
	private CourseService courseService;
	
	private final Faker faker = new Faker();
	private final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(StudentsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Check if data already exists
		long studentCount = studentService.getAllStudents().size();
		long instructorCount = instructorService.getAllInstructors().size();
		
		// Temporarily force data recreation
		if (false && (studentCount > 0 || instructorCount > 0)) {
			System.out.println("=== Database already contains data ===");
			System.out.println("Found " + studentCount + " students and " + instructorCount + " instructors");
			System.out.println("Skipping data population to avoid conflicts");
		} else {
			System.out.println("=== Populating Database with Java Faker ===");

			List<Instructor> instructors = createInstructors();
			System.out.println("Created " + instructors.size() + " instructors");
			
			List<Pack> packs = createPacks();
			System.out.println("Created " + packs.size() + " packs");
			
			List<Student> students = createStudents();
			System.out.println("Created " + students.size() + " students");
			
			List<Course> courses = createCourses(instructors, packs);
			System.out.println("Created " + courses.size() + " courses");
		}
		
		System.out.println("\n=== Testing CRUD Operations for Courses ===");
		testCourseCRUDOperations();
		
		System.out.println("\n=== Testing Custom Queries ===");
		testCustomQueries();
	}
	
	private List<Instructor> createInstructors() {
		List<Instructor> instructors = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Instructor instructor = new Instructor();
			instructor.setName(faker.name().fullName());
			instructor.setEmail(faker.internet().emailAddress());
			instructors.add(instructorService.saveInstructor(instructor));
		}
		return instructors;
	}
	
	private List<Pack> createPacks() {
		List<Pack> packs = new ArrayList<>();
		String[] packNames = {"Mathematics", "Computer Science", "Physics", "Chemistry", "Biology", "Literature"};
		
		for (int year = 1; year <= 4; year++) {
			for (int semester = 1; semester <= 2; semester++) {
				for (String packName : packNames) {
					Pack pack = new Pack();
					pack.setYear(year);
					pack.setSemester(semester);
					pack.setName(packName + " - Year " + year + " Semester " + semester);
					packs.add(packService.savePack(pack));
				}
			}
		}
		return packs;
	}
	
	private List<Student> createStudents() {
		List<Student> students = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			Student student = new Student();
			student.setCode("STU" + String.format("%03d", i + 1));
			student.setName(faker.name().fullName());
			student.setEmail(faker.internet().emailAddress());
			student.setYear(random.nextInt(4) + 1); // Years 1-4
			students.add(studentService.saveStudent(student));
		}
		return students;
	}
	
	private List<Course> createCourses(List<Instructor> instructors, List<Pack> packs) {
		List<Course> courses = new ArrayList<>();
		String[] courseNames = {
			"Advanced Algorithms", "Database Systems", "Web Development", "Machine Learning",
			"Software Engineering", "Computer Networks", "Operating Systems", "Data Structures",
			"Artificial Intelligence", "Cybersecurity", "Mobile Development", "Cloud Computing"
		};
		
		for (int i = 0; i < 30; i++) {
			Course course = new Course();
			course.setCode("CS" + String.format("%03d", i + 1));
			course.setAbbr("CS" + (i + 1));
			course.setName(courseNames[random.nextInt(courseNames.length)]);
			course.setType(random.nextBoolean() ? CourseType.COMPULSORY : CourseType.OPTIONAL);
			course.setInstructor(instructors.get(random.nextInt(instructors.size())));
			course.setPack(packs.get(random.nextInt(packs.size())));
			course.setGroupCount(random.nextInt(5) + 1);
			course.setDescription(faker.lorem().paragraph());
			courses.add(courseService.saveCourse(course));
		}
		return courses;
	}
	
	private void testCourseCRUDOperations() {
		// CREATE
		System.out.println("CREATE: Courses created with Java Faker");
		
		// READ
		List<Course> allCourses = courseService.getAllCourses();
		System.out.println("READ: Found " + allCourses.size() + " courses");
		
		if (!allCourses.isEmpty()) {
			Course firstCourse = allCourses.get(0);
			System.out.println("  First course: " + firstCourse.getName() + " (" + firstCourse.getCode() + ")");
			
			// UPDATE
			String originalName = firstCourse.getName();
			firstCourse.setName("Updated " + originalName);
			Course updatedCourse = courseService.saveCourse(firstCourse);
			System.out.println("UPDATE: Course name changed from '" + originalName + "' to '" + updatedCourse.getName() + "'");
			
			int updatedCount = courseService.updateGroupCountByCourseType(5, CourseType.COMPULSORY);
			System.out.println("TRANSACTIONAL UPDATE: Updated group count for " + updatedCount + " compulsory courses");
			
			// DELETE
			Course lastCourse = allCourses.get(allCourses.size() - 1);
			Long courseIdToDelete = lastCourse.getId();
			courseService.deleteCourse(courseIdToDelete);
			System.out.println("DELETE: Deleted course with ID " + courseIdToDelete);
			
			boolean exists = courseService.existsById(courseIdToDelete);
			System.out.println("  Course exists after deletion: " + exists);
		}
	}
	
	private void testCustomQueries() {
		// derived queries
		List<Course> compulsoryCourses = courseService.getCoursesByType(CourseType.COMPULSORY);
		System.out.println("Derived Query: Found " + compulsoryCourses.size() + " compulsory courses");
		
		// JPQL queries
		List<Course> coursesWithHighGroupCount = courseService.getCoursesWithGroupCountGreaterThan(3);
		System.out.println("JPQL Query: Found " + coursesWithHighGroupCount.size() + " courses with group count > 3");
		
		List<Course> firstYearCourses = courseService.getCoursesByPackYearAndSemester(1, 1);
		System.out.println("JPQL Query: Found " + firstYearCourses.size() + " courses for Year 1, Semester 1");
		
		List<Instructor> instructorsWithCourses = instructorService.getInstructorsWithCourses();
		System.out.println("Instructor Query: Found " + instructorsWithCourses.size() + " instructors with courses");
		
		List<Pack> packsWithCourses = packService.getPacksWithCourses();
		System.out.println("Pack Query: Found " + packsWithCourses.size() + " packs with courses");
		
	}
}
