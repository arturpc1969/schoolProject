package br.com.alura.school.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCourseEnrollRequest {
	@Size(max=20)
    @NotBlank
    @JsonProperty
	private String username;

	public NewCourseEnrollRequest(String username) {
		this.username = username;
	}
	
	public NewCourseEnrollRequest() {}

	public String getUsername() {
		return username;
	}
}
