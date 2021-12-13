package br.com.alura.school.course;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class CourseController {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final CourseUsersRepository courseUserRepository;

	CourseController(CourseRepository courseRepository, UserRepository userRepository, CourseUsersRepository courseUserRepository) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.courseUserRepository = courseUserRepository;
	}

	@GetMapping("/courses")
	ResponseEntity<List<CourseResponse>> allCourses() {
		List<Course> allCoursesList = courseRepository.findAll();
		List<CourseResponse> allCoursesResponse = new ArrayList<>();
		allCoursesList.forEach(course -> allCoursesResponse.add(new CourseResponse(course)));
		return ResponseEntity.ok(allCoursesResponse);
	}

	@GetMapping("/courses/{code}")
	ResponseEntity<CourseResponse> courseByCode(@PathVariable("code") String code) {
		Course course = courseRepository.findByCode(code).orElseThrow(
				() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", code)));
		return ResponseEntity.ok(new CourseResponse(course));
	}

	@PostMapping("/courses")
	ResponseEntity<Void> newCourse(@RequestBody @Valid NewCourseRequest newCourseRequest) {
		courseRepository.save(newCourseRequest.toEntity());
		URI location = URI.create(format("/courses/%s", newCourseRequest.getCode()));
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/courses/{code}/enroll")
	ResponseEntity<Void> newEnroll(@PathVariable("code") String code,
			@RequestBody @Valid NewCourseEnrollRequest newCourseEnrollRequest) {
		Course course = courseRepository.findByCode(code).orElseThrow(
				() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", code)));
		User user = userRepository.findByUsername(newCourseEnrollRequest.getUsername())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
						format("User %s not found", newCourseEnrollRequest.getUsername())));
		CourseUsers actualCourseUser = new CourseUsers(course, user);

		List<CourseUsers> usersEnrolled = course.getUsers();

		if (!usersEnrolled.contains(actualCourseUser)) {
			course.addUserEnroll(user);
			courseRepository.flush();
			return ResponseEntity.created(null).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/courses/enroll/report")
	public List<ItemCourseEnrollReport> courseEnrollReport() {
		return courseUserRepository.courseEnrollReport();
		
	}
	
}
