package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Test")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionTest {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;
	private String testName;
	private String testDescription;
	
	@ManyToMany(mappedBy = "tests")
	@JsonIgnore
	private List<Question> question;
	
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public List<Question> getQuestion() {
		return question;
	}
	public void setQuestion(List<Question> question) {
		this.question = question;
	}
	public QuestionTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuestionTest(Long testId, String testName, String testDescription, List<Question> question) {
		super();
		this.testId = testId;
		this.testName = testName;
		this.testDescription = testDescription;
		this.question = question;
	}
	@Override
	public String toString() {
		return "Test [testId=" + testId + ", testName=" + testName + ", testDescription=" + testDescription
				+ ", question=" + question + "]";
	}

	
}
