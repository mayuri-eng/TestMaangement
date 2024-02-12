package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionTest;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.QuestionTestNotFoundException;
import com.example.demo.service.QuestionTestService;

@RestController
@RequestMapping(value = "/api/v1/test")
public class QuestionTestController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(QuestionTestController.class);

	@Autowired
	QuestionTestService testService;

	@PostMapping
	public QuestionTest saveData(@RequestBody QuestionTest test) {
		QuestionTest questionTest = new QuestionTest();
		try {
			questionTest = testService.save(test);
			log.info("QuestionTest Request:{}", test);
			return questionTest;
		} catch (QuestionTestNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QuestionTest not found " + e);

		}

	}


	@GetMapping
	public List<QuestionTest> findAll() {
		try {
			log.info("getting all questionTest");
			return testService.findAll();

		} catch (QuestionTestNotFoundException e) {
			log.error("Error retrieving questionsTest: {}", e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving questionsTest", e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		try {
			if (id != null) {
				QuestionTest question = testService.findById(id);
				log.info("Getting with id questionTest");
				return ResponseEntity.ok(question);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (QuestionTestNotFoundException e) {
			log.error("Exception in findById data in QuestionTestController: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("question id not found");
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody QuestionTest test) {
		try {
			if (test.getTestId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("QuestionTest ID is required for update");
			}
			log.info("Update Question Request: {}", test);
			QuestionTest questionUpdate = testService.update(test);
			return ResponseEntity.ok(questionUpdate);
		} catch (CategoryNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("QuestionTest not found");

		}
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		try {
			return testService.delete(id);
		} catch (CategoryNotFoundException e) {
			log.error("Error deleting test with ID {}: {}", id, e.getMessage(), e);
			return "Failed to delete test. Check logs for details.";
		}
	}
}
