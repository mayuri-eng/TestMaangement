//package com.example.demo.entity;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "Result")
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class TestResult {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	@ManyToOne
//	@JoinColumn(name = "employee_id")
//	private Employee employee;
//	@ManyToOne
//	@JoinColumn(name = "question_id")
//	private Question question;
//	private char answer;
//	private int score;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Employee getEmployee() {
//		return employee;
//	}
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}
//
//	public Question getQuestion() {
//		return question;
//	}
//
//	public void setQuestion(Question question) {
//		this.question = question;
//	}
//
//	public char getAnswer() {
//		return answer;
//	}
//
//	public void setAnswer(char answer) {
//		this.answer = answer;
//	}
//
//	public int getScore() {
//		return score;
//	}
//
//	public void setScore(int score) {
//		this.score = score;
//	}
//
//	@Override
//	public String toString() {
//		return "TestResult [id=" + id + ", employee=" + employee + ", question=" + question + ", answer=" + answer
//				+ ", score=" + score + "]";
//	}
//
//	public TestResult() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//}
