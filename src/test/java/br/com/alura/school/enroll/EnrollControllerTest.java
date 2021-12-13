package br.com.alura.school.enroll;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.course.NewCourseEnrollRequest;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void should_add_new_enroll() throws Exception {
    	courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
    	userRepository.save(new User("ana", "ana@email.com"));
    	
        NewCourseEnrollRequest newCourseEnrollRequest = new NewCourseEnrollRequest("ana");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newCourseEnrollRequest)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void should_not_enroll_user_already_enrolled() throws Exception {
    	courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
    	userRepository.save(new User("ana", "ana@email.com"));
    	
        NewCourseEnrollRequest newCourseEnrollRequest = new NewCourseEnrollRequest("ana");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newCourseEnrollRequest)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newCourseEnrollRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_enroll_non_existent_user() throws Exception {
    	courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
    	userRepository.save(new User("ana", "ana@email.com"));
    	
        NewCourseEnrollRequest newCourseEnrollRequest = new NewCourseEnrollRequest("analu");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newCourseEnrollRequest)))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    void should_not_enroll_in_non_existent_course() throws Exception {
    	courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
    	userRepository.save(new User("ana", "ana@email.com"));
    	
        NewCourseEnrollRequest newCourseEnrollRequest = new NewCourseEnrollRequest("ana");

        mockMvc.perform(post("/courses/java-3/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newCourseEnrollRequest)))
                .andExpect(status().is4xxClientError());
    }

}