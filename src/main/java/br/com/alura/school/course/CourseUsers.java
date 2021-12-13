package br.com.alura.school.course;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import br.com.alura.school.user.User;

@Entity(name = "CourseUsers")
@Table(name = "course_users")
public class CourseUsers {
	@EmbeddedId
    private CourseUsersId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @Column(name = "enroll_date")
    private LocalDate enrollDate = LocalDate.now();
 
    private CourseUsers() {}
 
    public CourseUsers(Course course, User user) {
        this.course = course;
        this.user = user;
        this.id = new CourseUsersId(course.getId(), user.getId());
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        CourseUsers that = (CourseUsers) o;
        return Objects.equals(course, that.course) &&
               Objects.equals(user, that.user);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(course, user);
    }
}
