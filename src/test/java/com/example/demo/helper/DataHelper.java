package com.example.demo.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Category;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionTest;

public class DataHelper {

	public static Category createTestCategory() {
		List<Question> question = new ArrayList<>();
		Category category = new Category();
		category.setId(1L);
		category.setName("java");
		category.setDescription("java Description");
		category.setQuestion(question);
		return category;
	}

	public static Question createTestQuestion() {
		Question question = new Question();
		Category category = new Category();
		List<QuestionTest> test = new ArrayList<>();
		question.setCategory(category);
		question.setCorrectOption("A");
		question.setMark("12");
		question.setOp1("A");
		question.setOp2("B");
		question.setQuestion("what is java");
		question.setQuestionid(1L);
		question.setTests(test);

		return question;
	}

	public static QuestionTest createQuestionTest() {
		List<Question> question = new ArrayList<>();
		QuestionTest questionTest = new QuestionTest();
		questionTest.setTestId(2L);
		questionTest.setTestName("java");
		questionTest.setTestDescription("java Description");
		questionTest.setQuestion(question);

		return questionTest;
	}

}
