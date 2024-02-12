package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;

import com.example.demo.controller.CategoryControllerTest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Question;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.QuestionRepo;

public class QuestionServiceImplTest {

	private static final Logger log = (Logger) LoggerFactory.getLogger(CategoryControllerTest.class);

	@Mock
	private QuestionRepo questionRepo;

	@Mock
	private CategoryRepo categoryRepo;

	@InjectMocks
	private QuestionServiceImpl questionService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	private MockMultipartFile validExcelFile;
	
	 @Before(value = "")
	    public void setup() throws IOException {
	        InputStream inputStream = getClass().getResourceAsStream("/valid_excel_file.xlsx");
	        validExcelFile = new MockMultipartFile("file", "valid_excel_file.xlsx", "application/vnd.ms-excel", inputStream.readAllBytes());
	    }

	@Test
	public void testSaveQuestionData() {
		try {
			String categoryName = "ExistingCategory";
			Category category = new Category();
			category.setName(categoryName);
			Question question = DataHelper.createTestQuestion();
			when(categoryRepo.findByName(categoryName)).thenReturn(Optional.of(category));
			when(questionRepo.save(question)).thenReturn(question);
			Question savedQuestion = questionService.save(categoryName, question);
			assertNotNull(savedQuestion);
			assertEquals(category, savedQuestion.getCategory());
		} catch (QuestionNotFoundException e) {
			log.info("error in category: " + e.getMessage());
		}
	}

	@Test
	public void testSaveQuestionDataException() {
		try {
			String categoryName = "";
			Category category = new Category();
			category.setName(categoryName);
			Question question = DataHelper.createTestQuestion();
			// question.getCategory().setName("");
			when(categoryRepo.findByName(categoryName))
					.thenThrow(new QuestionNotFoundException("Category name not found"));
			when(questionRepo.save(question)).thenReturn(question);
			Question savedQuestion = questionService.save(categoryName, question);

			// Question save = questionRepo.save(question);
			assertNotNull(savedQuestion);
			assertEquals(category, savedQuestion.getCategory());
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}

	@Test
	public void QuestionFindAll() {
		try {
			List<Question> question = new ArrayList<>();
			when(questionService.findall()).thenReturn(question);
			List<Question> responseEntity = questionRepo.findAll();
			assertEquals(question, responseEntity);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}

	@Test
	public void QuestionFindAllException() {
		try {
			when(questionRepo.findAll()).thenThrow(QuestionNotFoundException.class);
			questionService.findall();
		} catch (ServiceException e) {
			log.info("error in question: " + e.getMessage());
		}
	}

	@Test
	public void QuestionFindById() {
		Long id = 1L;
		try {

			Question question = DataHelper.createTestQuestion();

			when(questionRepo.findById(id)).thenReturn(Optional.of(question));

			Question result = questionService.findById(id);

			assertEquals(question, result);
		} catch (QuestionNotFoundException e) {
			assertEquals("Question not found with id: " + id, e.getMessage());
		}
	}

	@Test
	public void testFindByIdException() {
		Long id = null;
		try {

			Optional<Question> responseEntity = questionRepo.findById(id);
			assertNotNull(responseEntity);
		} catch (QuestionNotFoundException e) {
			assertEquals("Question not found with id: " + id, e.getMessage());
		}
	}

//
	@Test
	public void testUpdatequestion() {
		try {
			Question question = DataHelper.createTestQuestion();
			when(questionRepo.findById(1L)).thenReturn(Optional.of(question));
			when(questionRepo.save(question)).thenReturn(question);
			Question result = questionService.update(question);
			assertEquals(question, result);
		} catch (QuestionNotFoundException e) {
			log.info("error in question: " + e.getMessage());
		}
	}

	@Test
	public void testUpdatequestionException() {

		Question question = DataHelper.createTestQuestion();
		try {
			when(questionRepo.findById(1L)).thenReturn(Optional.of(question));
			when(questionRepo.save(question)).thenReturn(question);
			Question result = questionService.update(question);
			assertEquals(question, result);
		} catch (QuestionNotFoundException e) {
			assertEquals("Question not found with id: " + question.getQuestionid(), e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		Long id = 1L;
		try {

			Optional<Question> question = Optional
					.of(new Question(id, "question 1", "Description 1", "A", "B", "A", null, null));

			when(questionRepo.findById(id)).thenReturn(question);

			String result = questionService.delete(id);

			assertEquals("question id" + id + " deleted successfully", result);
		} catch (QuestionNotFoundException e) {
			assertEquals("Question not found with id: " + id, e.getMessage());
		}
	}

	@Test
	public void testDelete_Exception() {
		Long id = 1L;

		try {
			questionService.delete(id);
			when(questionRepo.findById(id)).thenReturn(Optional.empty());
		} catch (QuestionNotFoundException e) {
			assertEquals("Question not found with id: " + id, e.getMessage());
		}
	}
	
	 @Test
	    public void testImportQuestionsFromExcelSuccess() throws IOException {
	        try {
	        when(categoryRepo.findByName(anyString())).thenReturn(java.util.Optional.of(new Category()));
	        when(questionRepo.saveAll(any())).thenReturn(Collections.emptyList());
	        questionService.importQuestionsFromExcel(Collections.singletonList(validExcelFile));
	        verify(questionRepo, times(1)).saveAll(anyList());
	    }catch (Exception e) {
			e.getMessage();
		}
	 }
}
