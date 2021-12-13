package br.com.alura.school.course;

import br.com.alura.school.user.User;

public class ItemCourseEnrollReport {

	private String email;
	private Long quantidadeMatriculas;
	
	public ItemCourseEnrollReport(User user, Long quantidadeMatriculas) {
		this.email = user.getEmail();
		this.quantidadeMatriculas = quantidadeMatriculas;
	}
	
	public String getEmail() {
		return email;
	}

	public Long getQuantidadeMatriculas() {
		return quantidadeMatriculas;
	}
	
	
	
}
