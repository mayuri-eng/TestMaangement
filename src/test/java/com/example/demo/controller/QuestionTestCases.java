package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionTest;
import com.example.demo.exception.QuestionTestNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.service.QuestionTestService;

public class QuestionTestCases {
	private static final Logger log = (Logger) LoggerFactory.getLogger(QuestionTestCases.class);

	@Mock
	private QuestionTestService testService;

	@InjectMocks
	private QuestionTestController testController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSavQuestionTestData() {
		try {
			QuestionTest question = DataHelper.createQuestionTest();
			when(testService.save(any(QuestionTest.class))).thenReturn(question);
			QuestionTest savedQuestion = testController.saveData(question);
			assertNotNull(savedQuestion);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in QuestionTest: " + e.getMessage());
		}
	}

	@Test
	public void QuestionTestFindAll() {
		try {
			List<QuestionTest> question = new ArrayList<>();
			when(testService.findAll()).thenReturn(question);
			List<QuestionTest> responseEntity = testController.findAll();
			assertEquals(question, responseEntity);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in Question: " + e.getMessage());
		}
	}

	@Test
	public void QuestionTestFindById() {
		try {
			Long questionId = 1L;
			QuestionTest question = DataHelper.createQuestionTest();
			when(testService.findById(questionId)).thenReturn(question);
			ResponseEntity<?> responseEntity = testController.findById(1L);
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			QuestionTest actualQuestion = (QuestionTest) responseEntity.getBody();
			assertNotNull(actualQuestion);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in QuestionTest: " + e.getMessage());
		}
	}

	@Test
	public void testFindByIdQuestionTestException() {
		try {
			Long testId = null;
			when(testService.findById(testId))
					.thenThrow(new QuestionTestNotFoundException("QuestionTest Id not found"));

			ResponseEntity<?> responseEntity = testController.findById(null);
			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
			Question actualQuestion = (Question) responseEntity.getBody();
			assertNull(actualQuestion);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in questionTest: " + e.getMessage());
		}
//
	}

	@Test
	public void testUpdateQuestionTest() {
		try {
			QuestionTest question = DataHelper.createQuestionTest();
			when(testService.update(question)).thenReturn(question);
			ResponseEntity<?> updatedQuestion = testController.update(question);
			assertEquals(HttpStatus.OK, updatedQuestion.getStatusCode());
			assertNotNull(question);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in questionTest: " + e.getMessage());
		}
	}

	@Test
	public void testUpdateQuestionException() {
		try {
			QuestionTest question = new QuestionTest();
			when(testService.update(question))
					.thenThrow(new QuestionTestNotFoundException("QuestionTest ID is required for update"));
			ResponseEntity<?> updateQuestion = testController.update(question);
			assertEquals(HttpStatus.BAD_REQUEST, updateQuestion.getStatusCode());
			assertNotNull(updateQuestion);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in questionTest: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteQuestionTest() {
		try {
			Long questionId = 1L;
			String expectedResponse = "Deleted question with ID: " + questionId;
			when(testService.delete(questionId)).thenReturn(expectedResponse);
			String response = testController.delete(questionId);
			assertEquals(expectedResponse, response);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in questionTest: " + e.getMessage());
		}
	}

	@Test
	public void testDeleteQuestionException() {
		try {
			Long testId = null;
			String expectedResponse = "Failed to delete questionTest. Check logs for details.";
			when(testService.delete(testId)).thenReturn(expectedResponse);
			String response = testController.delete(testId);
			assertEquals(expectedResponse, response);
		} catch (QuestionTestNotFoundException e) {
			log.info("error in questionTest: " + e.getMessage());
		}
	}

}
