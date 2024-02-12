package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long category_id;
	private String name;
	private String description;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Question> question;

	public Long getId() {
		return category_id;
	}

	public void setId(Long category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Question> getQuestion() {
		return question;
	}

	public void setQuestion(List<Question> question) {
		this.question = question;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Long category_id, String name, String description, List<Question> question) {
		super();
		this.category_id = category_id;
		this.name = name;
		this.description = description;
		this.question = question;
	}

	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", name=" + name + ", description=" + description + ", question=" + question
				+ "]";
	}

	
}
