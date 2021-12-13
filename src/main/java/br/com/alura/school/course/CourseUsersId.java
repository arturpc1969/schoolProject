package br.com.alura.school.course;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CourseUsersId implements Serializable {

	@Column(name = "course_id")
	private Long courseId;

	@Column(name = "user_id")
	private Long userId;

	private CourseUsersId() { }

	public CourseUsersId(Long courseId, Long userId) {
		this.courseId = courseId;
		this.userId = userId;
	}
	
	

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		CourseUsersId that = (CourseUsersId) o;
		return Objects.equals(courseId, that.courseId) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseId, userId);
	}
}
