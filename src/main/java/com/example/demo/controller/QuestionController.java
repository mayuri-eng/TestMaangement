package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Category;
import com.example.demo.entity.Question;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.service.CategoryServiceimpl;
import com.example.demo.service.QuestionService;

@RestController
@RequestMapping(value = "/api/v1/question")
public class QuestionController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	QuestionService questionService;
	
	@Autowired
	CategoryServiceimpl categoryServiceimpl;

	@PostMapping
	public Question saveData(@RequestParam String categoryName,@RequestBody Question question) {
		try {
			Question questionObj = questionService.save(categoryName,question);
			log.info("Question Request:{}", questionObj);
			return questionObj;
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found for name" +  e);
		}

	}

	@GetMapping
	public List<Question> findAll() {
		try {
		log.info("Getting all Question");
		return questionService.findall();
		}catch (QuestionNotFoundException e) {
			log.error("Error retrieving questions: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving questions", e);		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		try {
			if (id != null) {
				Question question = questionService.findById(id);
				log.info("Getting with id question");
				return ResponseEntity.ok(question);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (QuestionNotFoundException e) {
			log.error("Exception in findById data in QuestionTestController: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("question id not found");
		}
		
		
		
	}

	

	@PutMapping
	public ResponseEntity<?> updateQuestion(@RequestBody Question question) {
		try {
			if (question.getQuestionid() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question ID is required for update");
			}
			log.info("Update Question Request: {}", question);
			Question questionUpdate = questionService.update(question);
			return ResponseEntity.ok(questionUpdate);
		} catch (QuestionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("question Id not found");

		}
	}
	
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		try {
			return questionService.delete(id);
		} catch (QuestionNotFoundException e) {
			log.error("Error deleting question with ID {}: {}", id, e.getMessage(), e);
			return "Failed to delete question. Check logs for details.";
		}
	}

	@PostMapping("/import")
	public ResponseEntity<String> importExcelToDatabase(@RequestParam("file") List<MultipartFile> files)
			throws IOException {
 
		try {
			log.info("Importing questions from Excel file");
			questionService.importQuestionsFromExcel(files);
			return ResponseEntity.ok("Added Successfully Questions Data");
		} catch (Exception e) {
			log.error("Error importing questions from Excel file: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
