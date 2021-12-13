package br.com.alura.school.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseUsersRepository extends JpaRepository<CourseUsers, CourseUsersId> {

	@Query("select new br.com.alura.school.course.ItemCourseEnrollReport(c.user, "
			+ "COUNT(c.course)) from CourseUsers c GROUP BY c.user")
	public List<ItemCourseEnrollReport> courseEnrollReport();

}
