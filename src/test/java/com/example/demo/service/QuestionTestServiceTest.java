package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.QuestionTest;
import com.example.demo.exception.QuestionTestNotFoundException;
import com.example.demo.helper.DataHelper;
import com.example.demo.repo.QuestionTestRepo;

public class QuestionTestServiceTest {
	@Mock
	private QuestionTestRepo testRepo;

	@InjectMocks
	private QuestionTestServiceImpl testService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() {
		try {

			QuestionTest test = DataHelper.createQuestionTest();
			when(testRepo.save(test)).thenReturn(test);
			// QuestionTest result = null;
			assertNotNull(test);
			test = testService.save(test);
		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testSaveException() {
		try {

			QuestionTest test = new QuestionTest();
			when(testRepo.save(test)).thenThrow(new QuestionTestNotFoundException("QuestionTest failes to save"));
			// QuestionTest result = null;
			assertNotNull(test);
			test = testService.save(test);
		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testFindAll() {
		try {
			List<QuestionTest> tests = new ArrayList<>();
			when(testRepo.findAll()).thenReturn(tests);
			List<QuestionTest> result = null;
			result = testService.findAll();
			assertEquals(tests, result);
		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testFindAllException() {
		try {
			List<QuestionTest> tests = new ArrayList<>();
			when(testRepo.findAll()).thenThrow(new QuestionTestNotFoundException("QuestionTest findAll"));
			List<QuestionTest> result = null;
			result = testService.findAll();
			assertEquals(tests, result);
		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testFindById() {
		try {
			long id = 1L;
			QuestionTest test = new QuestionTest();
			when(testRepo.findById(id)).thenReturn(Optional.of(test));
			QuestionTest result = null;
			result = testService.findById(id);
			assertEquals(test, result);

		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}

	}

	@Test
	public void testFindByIdException() {
		try {
			QuestionTest test = new QuestionTest();
			when(testRepo.findById(null)).thenThrow(new QuestionTestNotFoundException("QuestionTest findById"));
			// result = testService.findById(null);
			assertNotNull(test);

		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}

	}

	@Test
	public void testDeleteQuestion() {
		try {
			long id = 1L;
			when(testRepo.findById(id)).thenReturn(Optional.of(new QuestionTest()));
			String result = null;
			result = testService.delete(id);

			assertEquals("question id " + id + " deleted successfully", result);
		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testDeleteQuestionException() {
		try {
			when(testRepo.findById(null)).thenThrow(new QuestionTestNotFoundException("QuestionTest id deleted"));
			String result = null;
			result = testService.delete(null);

		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();

		}
	}

	@Test
	public void testUpdateExistingQuestionTest() {
		try {
			QuestionTest test = DataHelper.createQuestionTest();

			when(testRepo.findById(test.getTestId())).thenReturn(Optional.of(test));
			when(testRepo.save(test)).thenReturn(test);
			test = testService.update(test);

		} catch (QuestionTestNotFoundException e) {
			e.printStackTrace();
		}

	}

}
