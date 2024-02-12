package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.mock.web.MockMultipartFile;

import com.example.demo.entity.Question;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.service.QuestionService;

public class QuestionControllerTest {  
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(QuestionControllerTest.class);


	@Mock
	private QuestionService questionService;

	@InjectMocks
	private QuestionController questionController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void testSavQuestionData() {
		try { 
			  String categoryName = "NonExistingCategory";
			Question question = DataHelper.createTestQuestion();
			when(questionService.save(categoryName, question)).thenReturn(question);
			Question savedQuestion = questionController.saveData(categoryName, question);
			assertNotNull(savedQuestion);
		} catch (QuestionNotFoundException e) {
			log.info("error in Question: " + e.getMessage());
		}
	}

	@Test
	public void QuestionFindAll() {
		try {
			List<Question> question = new ArrayList<>();
			when(questionService.findall()).thenReturn(question);
			List<Question> responseEntity = questionController.findAll();			
			assertEquals(question, responseEntity);
		} catch (QuestionNotFoundException e) {
			log.info("error in Question: " + e.getMessage());
		}
	}
	
	
	@Test
	public void QuestionFindById() {
		try {
			Long questionId = 1L;
			Question question = DataHelper.createTestQuestion();
			when(questionService.findById(questionId)).thenReturn(question);
			ResponseEntity<?> responseEntity = questionController.findById(1L);
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
			Question actualQuestion = (Question) responseEntity.getBody();
			assertNotNull(actualQuestion);
		} catch (QuestionNotFoundException e) {
			log.info("error in Question: " + e.getMessage());
		}
	}

	
	
	@Test
	public void testFindByIdCategoryException() {
		try {
			Long questionId = null;
			when(questionService.findById(questionId)).thenThrow(new QuestionNotFoundException("Question Id not found"));

			ResponseEntity<?> responseEntity = questionController.findById(null);
			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
			Question actualQuestion = (Question) responseEntity.getBody();
			assertNull(actualQuestion);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
//
	}
	@Test
	public void testUpdateQuestion() {
		try {
			Question question = DataHelper.createTestQuestion();
			when(questionService.update(question)).thenReturn(question);
			ResponseEntity<?> updatedQuestion = questionController.updateQuestion(question);
			assertEquals(HttpStatus.OK, updatedQuestion.getStatusCode());
			Question actualQuestion= (Question) updatedQuestion.getBody();
			assertEquals(question, actualQuestion);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateQuestionException() {
		try {
			Question question = new Question();
			when(questionService.update(question)).thenThrow(new QuestionNotFoundException("Question ID is required for update"));
			ResponseEntity<?> updateQuestion = questionController.updateQuestion(question);
			assertEquals(HttpStatus.BAD_REQUEST, updateQuestion.getStatusCode());
			assertNotNull(updateQuestion);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}
	@Test
	public void testDeleteCategory() {
		try {
			Long questionId = 1L;
			String expectedResponse = "Deleted question with ID: " + questionId;
			when(questionService.delete(questionId)).thenReturn(expectedResponse);
			String response = questionController.delete(questionId);
			assertEquals(expectedResponse, response);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}
	
	@Test
	public void testDeleteCategoryException() {
		try {
			Long questionId = 1L;
			String expectedResponse = "Failed to delete question. Check logs for details.";
			when(questionService.delete(questionId)).thenThrow(new QuestionNotFoundException("Question ID is required for update"));
			String response = questionController.delete(questionId);
			assertEquals(expectedResponse, response);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}
	
	
	@Test
    public void testImportExcelToDatabase_Success() throws IOException {
		try {
			MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel", "data".getBytes());
	        doNothing().when(questionService).importQuestionsFromExcel(Collections.singletonList(file));
	        ResponseEntity<String> response;

	        response = questionController.importExcelToDatabase(Collections.singletonList(file));
            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Added Successfully Questions Data", response.getBody());
    }catch (IOException e) {
        // Handle the exception (if any)
        e.printStackTrace();
    }
	}
    @Test
    public void testImportExcelToDatabase_Failure() throws IOException {
    	try {
        MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel", "data".getBytes());
        doThrow(new IOException()).when(questionService).importQuestionsFromExcel(Collections.singletonList(file));
        ResponseEntity<String> response = questionController.importExcelToDatabase(Collections.singletonList(file));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    	}catch (IOException e) {
            // Handle the exception (if any)
            e.printStackTrace();
        }
    }
}
