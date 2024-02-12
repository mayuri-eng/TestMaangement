package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionid;
	private String question;
	private String op1;
	private String op2;
	private String correctOption;
	private String Mark;
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "question_test", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "test_id"))
	private List<QuestionTest> tests;

	public Long getQuestionid() {
		return questionid;
	}

	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}

	public String getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(String correctOption) {
		this.correctOption = correctOption;
	}

	public String getMark() {
		return Mark;
	}

	public void setMark(String mark) {
		Mark = mark;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<QuestionTest> getTests() {
		return tests;
	}

	public void setTests(List<QuestionTest> tests) {
		this.tests = tests;
	}

	public Question(Long questionid, String question, String op1, String op2, String correctOption, String mark,
			Category category, List<QuestionTest> tests) {
		super();
		this.questionid = questionid;
		this.question = question;
		this.op1 = op1;
		this.op2 = op2;
		this.correctOption = correctOption;
		Mark = mark;
		this.category = category;
		this.tests = tests;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Question [questionid=" + questionid + ", question=" + question + ", op1=" + op1 + ", op2=" + op2
				+ ", correctOption=" + correctOption + ", Mark=" + Mark + ", category=" + category + ", tests=" + tests
				+ "]";
	}

}
