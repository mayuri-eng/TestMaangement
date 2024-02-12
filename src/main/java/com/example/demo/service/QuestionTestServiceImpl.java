package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionTest;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.QuestionTestNotFoundException;
import com.example.demo.repo.QuestionTestRepo;

@Service
public class QuestionTestServiceImpl implements QuestionTestService {

	@Autowired
	QuestionTestRepo testRepo;

	@Override
	public QuestionTest save(QuestionTest test) {
		try {
			return testRepo.save(test);
		} catch (QuestionTestNotFoundException e) {
			throw new QuestionTestNotFoundException("Error saving QuestionTest: " + e.getMessage());
		}
	}

	@Override
	public List<QuestionTest> findAll() {
		try {
			return testRepo.findAll();
		} catch (QuestionTestNotFoundException e) {
			throw new QuestionTestNotFoundException("Error saving QuestionTest: " + e.getMessage());
		}
	}

	@Override
	public QuestionTest findById(Long id) {
		try {
			Optional<QuestionTest> question = testRepo.findById(id);
			if (question.isPresent()) {
				return testRepo.findById(id).get();
			} else {
				throw new CategoryNotFoundException("QuestionTest not found for ID: " + id);

			}

		} catch (QuestionTestNotFoundException e) {
			throw new ServiceException("Error retrieving category: " + e.getMessage(), e);

		}
	}

	@Override
	public String delete(Long id) {
		Optional<QuestionTest> test = testRepo.findById(id);
		if (test.isEmpty()) {
			return "id is not present";
		} else {
			testRepo.deleteById(id);
			return "question id " + id + " deleted successfully";
		}

	}

	@Override
	public QuestionTest update(QuestionTest test) {

		long questionId = test.getTestId();
		try {
			QuestionTest questionTest = findById(questionId);
			if (questionTest == null) {
				throw new QuestionTestNotFoundException("QuestionTest not found for ID: " + questionTest);
			}

			return testRepo.save(test);
		} catch (CategoryNotFoundException e) {
			throw new ServiceException("Error updating QuestionTest: " + e.getMessage(), e);
		}

	}

}
