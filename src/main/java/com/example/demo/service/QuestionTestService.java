package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.QuestionTest;


public interface QuestionTestService {

	public QuestionTest save(QuestionTest test);

	public List<QuestionTest> findAll();

	public QuestionTest findById(Long id);

	public QuestionTest update(QuestionTest test);

	public String delete(Long id);



}
